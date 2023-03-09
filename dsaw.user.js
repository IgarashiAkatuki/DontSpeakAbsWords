// ==UserScript==
// @name         DontSpeakAbsWords
// @namespace    https://github.com/IgarashiAkatuki/DontSpeakAbsWords
// @version      0.2
// @description  黑话/缩写翻译站
// @author       Jitsu
// @match        *://*/*
// @icon         https://project.midsummra.com/favicon.ico
// @grant        none
// ==/UserScript==

(function () {
    const DSAW_URL = "https://project.midsummra.com/api/"
    const DSAW_GLOBAL = {
        select: '',
        current: '',
        pos: {
            X: 0,
            Y: 0
        }
    }
    const setDSAW_GLOBAL = (event) => {
        DSAW_GLOBAL.select = window.getSelection().toString().trim()
        DSAW_GLOBAL.pos.X = event.pageX
        DSAW_GLOBAL.pos.Y = event.pageY
    }
    const getDSAW_Translations = (text) => {
        return new Promise((resolve, reject) => {
            let dsawGetXhr = new XMLHttpRequest();
            dsawGetXhr.onreadystatechange = () => {
                if (dsawGetXhr.readyState == 4 && dsawGetXhr.status == 200) {
                    let response = JSON.parse(dsawGetXhr.responseText);
                    let dsawData = {}
                    if (response.data != null) {
                        response.data.forEach(e => {
                            dsawData[e.translation] = e.likes
                        });
                    } else {
                        dsawData['没有查询到相关释义'] = null
                    }
                    resolve(dsawData)
                }
            }
            dsawGetXhr.ontimeout = () => {
                resolve({ 'error: 查询超时，请检查网络连接': null })
            }
            dsawGetXhr.onerror = () => {
                resolve({ 'error: 连接失败': null })
            }
            dsawGetXhr.open("POST", DSAW_URL + 'getTranslationsFromPersistence', true);
            dsawGetXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            dsawGetXhr.timeout = 3000
            dsawGetXhr.send('word=' + text);
        })
    }
    const subDSAW_Translations = (translation) => {
        return new Promise((resolve, reject) => {
            let dsawSubXhr = new XMLHttpRequest();
            dsawSubXhr.onreadystatechange = () => {
                if (dsawSubXhr.readyState == 4 && dsawSubXhr.status == 200) {
                    let response = JSON.parse(dsawSubXhr.responseText);
                    console.log(response)
                    if (response.code == 0) {
                        resolve(true)
                    } else {
                        resolve(false)
                    }
                }
            }
            dsawSubXhr.ontimeout = () => {
                resolve(false)
            }
            dsawSubXhr.onerror = () => {
                resolve(false)
            }
            dsawSubXhr.open("POST", DSAW_URL + 'submitTranslationsToTemp', true);
            dsawSubXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            dsawSubXhr.timeout = 3000
            dsawSubXhr.send('word=' + DSAW_GLOBAL.current + '&translation=' + translation);
        })
    }

    async function DSAW() {
        DSAW_GLOBAL.current = DSAW_GLOBAL.select
        let dsawPopup = document.getElementById('dsaw-popup')
        let dsawPopupTitleContent = document.getElementById('dsaw-popup-title-content')
        let dsawPopupContent = document.getElementById('dsaw-popup-content')
        if (window.innerWidth - DSAW_GLOBAL.pos.X < 400) {
            dsawPopup.style.left = (DSAW_GLOBAL.pos.X - 400).toString() + 'px'
        } else {
            dsawPopup.style.left = DSAW_GLOBAL.pos.X.toString() + 'px'
        }
        dsawPopup.style.top = DSAW_GLOBAL.pos.Y.toString() + 'px'
        dsawPopupTitleContent.innerText = DSAW_GLOBAL.select
        dsawPopup.style.display = 'block'
        let data = await getDSAW_Translations(DSAW_GLOBAL.select)
        let content = '';
        for (let i in data) {
            if (data[i] == null) {
                content += i
                break
            }
            content += i + '  |  ' + data[i] + ' ⭐\n'
        }
        dsawPopupContent.innerText = content
    }
    function submitTranslation() {
        let translation = prompt('请输入释义：')
        if (translation == null || translation == '' || translation.match(/^\s+$/)) {
            return
        }
        let result = subDSAW_Translations(translation)
        if (result) {
            alert('提交成功')
        } else {
            alert('提交失败')
        }
    }

    function redirectWebsite() {
        window.open("https://project.midsummra.com")
    }

    function hidePopup() {
        let dsawPopup = document.getElementById('dsaw-popup')
        if (dsawPopup.style.display == 'block') {
            dsawPopup.style.display = 'none'
            document.getElementById('dsaw-popup-content').innerText = '加载中……'
        }
    }

    let dom = document.createElement('div')
    let div = `
    <dsaw id="dsaw-popup" style="left:0px;top:0px;display:none">
        <dsaw id="dsaw-popup-title">
            <dsaw id="dsaw-popup-title-content">Title</dsaw>
            <dsaw class="dsaw-hr"></dsaw>
        </dsaw>
        <dsaw id="dsaw-popup-content">
            加载中……
        </dsaw>
        <dsaw id="dsaw-popup-footer">
            <dsaw class="dsaw-hr"></dsaw>
            <dsaw id="submit-translation">提交释义</dsaw>
            <dsaw id="redirect-website">转到网站</dsaw>
        </dsaw>
    </dsaw>
    `
    let css = `
    <style>
        dsaw {
            line-height: 1.2;
            display: block;
            margin: 0;
            padding: 0;
        }
        #dsaw-popup {
            font-size: 16px;
            position: absolute;
            width: 400px;
            box-sizing: unset;
            color: #000;
            border-radius: 8px;
            background-color: white;
            box-shadow: 5px 5px 15px 1px #c8c8c8;
            z-index: 999999999;
        }
        #dsaw-popup-title-content {
            display: flex;
            align-items: center;
            font-size: 24px;
            font-weight: bolder;
            page-break-after: avoid;
            white-space: nowrap;
            overflow: hidden;
            margin: 8px 15px;
        }
        #dsaw-popup-content {
            height: 100px;
            line-height: 25.5px;
            margin: 0;
            padding: 10px 18px;
            overflow: auto;
        }
        #dsaw-popup-footer {
            position: relative;
            bottom: 0;
            width: 100%;
            margin: 0;
        }
        #submit-translation {
            display: inline-block;
            margin: 10px 15px;
            cursor: pointer;
        }
        #redirect-website {
            display: inline-block;
            margin: 10px 15px;
            cursor: pointer;
            float: right;
        }
        .dsaw-hr {
            border-bottom: 1px solid #c6c6c6;
        }
    </style>
    `
    dom.innerHTML += div
    dom.innerHTML += css
    document.body.appendChild(dom)
    document.getElementById('dsaw-popup').addEventListener('mousedown', (event) => {
        event.stopPropagation()
    })
    document.getElementById('submit-translation').addEventListener('click', submitTranslation)
    document.getElementById('redirect-website').addEventListener('click', redirectWebsite)
    window.addEventListener("mouseup", setDSAW_GLOBAL);
    window.onkeydown = function (event) {
        if ((event.ctrlKey || event.altKey) && (event.keyCode == 191 || event.keyCode == 192)) {
            event.preventDefault()
            if (DSAW_GLOBAL.select == '' || DSAW_GLOBAL.select.length > 50 || DSAW_GLOBAL.select.match(/^\s+$/)) {
                return
            }
            DSAW()
        }
    }
    window.addEventListener("mousedown", hidePopup);
})()
