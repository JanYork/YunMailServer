<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>正在登陆</title>
    <style>
        #loading_box {
            width: 100%;
            height: 100%;
            background-color: #fff;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 9999;
        }
    </style>
</head>

<body>
<!--登录时加载页面-->
<div id="loading_box">
    <div id="lottie" style="width: 100%; height: 100%;"></div>
</div>
<script src="https://lf6-cdn-tos.bytecdntp.com/cdn/expire-1-M/lottie-web/5.8.1/lottie.min.js"></script>
<script>
    const animation = lottie.loadAnimation({
        container: document.getElementById('lottie'),
        renderer: 'svg',
        loop: true,
        autoplay: true,
        name: 'login_loading',
        path: '/json/loading.json'
    });
    window.onload = function () {
        animation.play();
        let msg = {
            code: ${auth.code},
            msg: '${auth.msg}',
            token: {
                tokenName: '${auth.token.tokenName}',
                tokenValue: '${auth.token.tokenValue}',
            }
        }
        try {
            window.opener.postMessage(msg, 'http://localhost:3000');
            window.close();
        } catch (e) {
            alert("登录失败，请重试");
            window.close();
        }
    }
</script>
</body>
</html>