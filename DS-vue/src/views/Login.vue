<template>
    <el-row class="login-container" :style="backgroundDiv">
        <el-col :span="8" class="login-container"></el-col>
        <el-col :span="8" class="login-container">
            <el-form
                    ref="loginForm"
                    :model="loginForm"
                    class="loginForm"
                    :rules="submitRules"
                    label-position="left"
            >
                <el-form-item>
                    <div class="login-title">学生宿舍后台管理系统</div>
                </el-form-item>

                <el-form-item label="用户名:" label-width="70px" prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>

                <el-form-item label="密码:" label-width="70px" prop="password">
                    <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" show-password></el-input>
                </el-form-item>

                <el-form-item label prop="code">
                    <el-row :gutter="10">
                        <el-col :span="16">
                            <el-input v-model="loginForm.code" placeholder="请输入验证码"></el-input>
                        </el-col>
                        <el-col :span="8">
                            <el-image
                                    style="width: 100%;  cursor: pointer;"
                                    :src="imageUrl"
                                    @click="renewalImage"
                            >
                            </el-image>
                        </el-col>
                    </el-row>
                </el-form-item>

                <el-form-item>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-button @click="submit()" class="my-button" type="primary">登录</el-button>
                        </el-col>
                        <el-col :span="12">
                            <el-button class="my-button">重置</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
            </el-form>
        </el-col>
        <el-col :span="8" class="login-container"></el-col>
    </el-row>
</template>

<script>
    export default {
        data() {
            return {
                imageUrl: 'http://localhost:8081/login/image?t=' + new Date().getTime(),
                backgroundDiv: {
                    backgroundImage: "url(" + require("@/assets/images/bg.png") + ")",
                    backgroundRepeat: "no-repeat",
                    backgroundSize: "100% 100%",
                },
                loginForm: {
                    username: "",
                    password: "",
                    code: "",
                },
                submitRules: {
                    username: [
                        {
                            required: true,
                            trigger: "change",
                            message: "请输入用户名",
                        },
                    ],
                    password: [
                        {
                            required: true,
                            trigger: "change",
                            message: "请输入密码",
                        },
                    ],
                    code: {
                        required: true,
                        trigger: "change",
                        message: "请输入验证码",
                    },
                },
            };
        },
        methods: {
            renewalImage() {
                this.imageUrl = 'http://localhost:8081/login/image?t=' + new Date().getTime();
            },
            submit() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        //后台登录成功返回的菜单数据
                        var data = new FormData();
                        data.append('username', this.loginForm.username);
                        data.append('password', this.loginForm.password);
                        data.append('code', this.loginForm.code);
                        this.$axios({
                            method: "post",
                            url: "api/login",
                            data: data,
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        }).then((result) => {
                            let recode = "404";
                            recode = result.data.code;
                            if (recode == 200) {
                                let menuList = result.data.data.menuList;
                                // //路由数据
                                let routerList = [];
                                let chi = [];
                                for (let i = 0; i < menuList.length; i++) {
                                    chi.push(menuList[i].children);
                                }
                                for (let i = 0; i < chi.length; i++) {
                                    for (let j = 0; j < chi[i].length; j++) {
                                        if (chi[i][j].children){
                                            for (let k=0;k<chi[i][j].children.length;k++){
                                                routerList.push(chi[i][j].children[k]);
                                            }
                                        }
                                        routerList.push(chi[i][j]);
                                    }
                                }
                                let user=result.data.data;
                                console.log(user);
                                //存储用户信息
                                sessionStorage.setItem("user", JSON.stringify(user));
                                sessionStorage.setItem("token", result.data.data.token);
                                //存储菜单数据
                                sessionStorage.setItem("menuList", JSON.stringify(menuList));
                                //存储路由数据
                                sessionStorage.setItem("routerList", JSON.stringify(routerList));
                                //提交的store
                                this.$store.commit("getMenuList", this.$router);
                                // 登录成功跳转到首页
                                this.$router.push("home");
                            } else {
                                this.$message({
                                    showClose: true,
                                    message: result.data.msg,
                                    type: 'error'
                                });
                                this.renewalImage();
                                this.$refs['loginForm'].resetFields();
                            }
                        });
                    }
                });
            },
        },
    };
</script>

<style>
    .login-container {
        height: 100%;
    }

    .login-title {
        font-size: 24px;
        font-weight: 600;
        text-align: center;
    }

    .loginForm {
        height: 300px;
        width: 350px;
        border-radius: 10px;
        box-shadow: 0 0 25px #cac6c6;
        padding: 20px 30px;
        margin: 33% auto;
        background: #fff;
    }

    .my-button {
        width: 100%;
    }
    .el-form-item{
        margin-bottom: 22px !important;
    }
</style>