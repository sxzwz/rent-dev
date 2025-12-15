<template>
    <div class="top-container">
        <div class="top">
            <div class="logo">
                <Logo sysName="房屋租赁系统 - 游客首页" textColor="rgb(255,255,255)" />
                <div @click="goLogin" class="nav-operation">
                    登录/注册
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Logo from '@/components/Logo.vue';
export default {
    components: { Logo },
    data() {
        return {

        }
    },
    created() {

    },
    methods: {

        goLogin() {
            window.open('/login', '_blank');
        },

    }
}
</script>

<style scoped lang="scss">
.top-container{
    background-color: rgb(32,32,32);
    color: rgb(255,255,255);
    height: 100vh;
}
.top{
    display: flex;
    justify-content: center;
    align-items: center;
    div{
        width: 300px;
        height: 200px;
    }
    .nav-operation{
        text-align: center;
        background-color: rgb(51,51,51);
        color: rgb(255,255,255);
        height: 50px;
        line-height: 50px;
        border-radius: 10px;
        &:hover{
            background-color: rgb(87, 60, 217);
            cursor: pointer;
        }
    }
}
</style>