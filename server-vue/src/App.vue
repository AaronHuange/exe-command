<template>
    <div id="app">
        <router-view class="login"></router-view>
    </div>
</template>

<script>
    export default {
        name: 'App'
    }
</script>

<style>
    body {
        margin: 0;
        padding: 0;
        overflow: hidden;
        width: 100%;
        height: 100%;
    }

    #app {
        width: 100%;
        height: 100%;
    }

    .login {
        width: 100%;
        height: 100%;
    }
</style>
