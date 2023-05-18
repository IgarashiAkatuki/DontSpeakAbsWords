// ==UserScript==
// @name         DontSpeakAbsWords
// @namespace    https://github.com/IgarashiAkatuki/DontSpeakAbsWords
// @version      0.4
// @description  黑话/缩写翻译站
// @author       Jitsu
// @homepage     https://github.com/anosu/DontSpeakAbsWords-UserScript
// @downloadURL  https://fastly.jsdelivr.net/gh/anosu/DontSpeakAbsWords-UserScript@main/dsaw.user.js
// @supportURL   https://github.com/anosu/DontSpeakAbsWords-UserScript/issues
// @match        *://*/*
// @icon         https://project.midsummra.com/favicon.ico
// @grant        GM_xmlhttpRequest
// @connect      project.midsummra.com
// @run-at       document-body
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
            GM_xmlhttpRequest({
                url: DSAW_URL + 'getTranslationsFromPersistence',
                method: 'POST',
                headers: {
                    "content-type": "application/x-www-form-urlencoded"
                },
                data: 'word=' + text,
                responseType: 'json',
                timeout: 3000,
                onload: (response) => {
                    if (response.status == 200) {
                        let res = response.response
                        let dsawData = {}
                        if (res.data != null) {
                            res.data.forEach(e => {
                                dsawData[e.translation] = e.likes
                            })
                        } else {
                            dsawData['没有查询到相关释义'] = null
                        }
                        resolve(dsawData)
                    } else {
                        resolve({ 'error: 请求失败': null })
                    }
                },
                ontimeout: () => {
                    resolve({ 'error: 查询超时，请检查网络连接': null })
                },
                onerror: () => {
                    resolve({ 'error: 连接失败': null })
                }
            })
        })
    }
    const subDSAW_Translations = (translation) => {
        return new Promise((resolve, reject) => {
            GM_xmlhttpRequest({
                url: DSAW_URL + 'submitTranslationsToTemp',
                method: 'POST',
                headers: {
                    "content-type": "application/x-www-form-urlencoded"
                },
                data: 'word=' + DSAW_GLOBAL.current + '&translation=' + translation,
                responseType: 'json',
                timeout: 3000,
                onload: (response) => {
                    if (response.status == 200) {
                        let res = response.response;
                        if (res.code == 0) {
                            resolve(true)
                        } else {
                            resolve(false)
                        }
                    } else {
                        resolve(false)
                    }
                },
                ontimeout: () => {
                    resolve(false)
                },
                onerror: () => {
                    resolve(false)
                }
            })
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
            height: 120px;
            line-height: 25.5px;
            margin: 0;
            padding: 10px 18px;
            overflow: auto;
            overscroll-behavior: none;
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
