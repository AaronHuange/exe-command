<template>
    <div id="app">
        <Login class="login"/>
    </div>
</template>

<script>
    import Login from "./view/Login";

    export default {
        name: 'App',
        components: {
            Login
        }
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

    .login{
        width: 100%;
        height: 100%;
    }
</style>
