<template>
    <el-main>
        <el-row :gutter="20">
            <el-col :span="7">
                <el-card shadow="always" :body-style="{ padding: '10px' }">
                    <span>个人信息</span>
                    <el-divider></el-divider>
                    <div style="text-align: center">
                        <userAvatar :user="user"/>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-user-solid"/>账号
            </span>
                        <Span>{{user.user.username}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-s-custom"/>用户昵称
            </span>
                        <Span>{{user.user.userNickname}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-location"/>学院
            </span>
                        <Span>{{user.major.majorName}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-message"/>班级
            </span>
                        <Span>{{user.student.stuClass}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-school"/>宿舍楼
            </span>
                        <Span>{{user.checkin.chBld}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-house"/>宿舍号
            </span>
                        <Span>{{user.checkin.chRoom}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-thumb"/>床位
            </span>
                        <Span>{{user.checkin.chBed}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" :class="user.student.stuSex==1?'el-icon-male':'el-icon-female'"/>性别
            </span>
                        <Span>{{user.student.stuSex==1?'男':'女'}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-mobile-phone"/>手机号
            </span>
                        <Span>{{user.user.userTell}}</Span>
                    </div>
                    <el-divider></el-divider>

                    <div style="height: 20px"></div>
                </el-card>
            </el-col>
            <el-col :span="17">
                <el-card shadow="always">
                    <span>基本资料</span>
                    <el-divider></el-divider>
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="用户资料" name="用户资料">
                            <el-form :model="msgForm" label-position="right" label-width="80">
                                <el-form-item label="用户昵称">
                                    <el-input v-model="msgForm.nickName" placeholder></el-input>
                                </el-form-item>
                                <el-form-item label="手机号">
                                    <el-input v-model="msgForm.tell" placeholder></el-input>
                                </el-form-item>
                                <el-form-item label="性别">
                                    <el-radio-group v-model="msgForm.sex">
                                        <el-radio :label="1">男</el-radio>
                                        <el-radio :label="0">女</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                            </el-form>
                            <div class="myButton">
                                <el-button @click="updata" type="primary" round size="mini">保存修改</el-button>
                                <el-button @click="reset" type="success" round size="mini">重置信息</el-button>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="修改密码" name="修改密码">
                            <el-form :model="passwordForm" label-position="right" label-width="80">
                                <el-form-item label="旧密码">
                                    <el-input show-password v-model="passwordForm.oldPassword" placeholder></el-input>
                                </el-form-item>
                                <el-form-item label="新密码">
                                    <el-input show-password v-model="passwordForm.newPassword" placeholder></el-input>
                                </el-form-item>
                                <el-form-item label="确认密码">
                                    <el-input show-password v-model="passwordForm.renewlyPassword" placeholder></el-input>
                                </el-form-item>
                            </el-form>
                            <div class="myButton">
                                <el-button @click="updataPas" type="primary" round size="mini">保存修改</el-button>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </el-card>
            </el-col>
        </el-row>
    </el-main>
</template>

<script>
    import userAvatar from "./userAvatar";
    export default {
        methods:{
            updataPas(){
                if (this.passwordForm.oldPassword){
                    if (this.passwordForm.newPassword){
                        if (this.passwordForm.renewlyPassword){
                            if (this.passwordForm.newPassword==this.passwordForm.renewlyPassword){
                                this.$axios({
                                    method: "put",
                                    url: "api/my/setPas",
                                    params: this.passwordForm,
                                }).then((result) => {
                                    this.passwordForm={
                                        userId:JSON.parse(sessionStorage.getItem('user')).userId,
                                        oldPassword:'',
                                        newPassword:'',
                                        renewlyPassword:''
                                    }
                                    this.$message({
                                        message: result.data,
                                        type: 'warning'
                                    });
                                })
                            }else{
                                this.$message.error('再次密码与新密码不一致！');
                            }
                        }else{
                            this.$message.error('再次输入新密码不能为空！');
                        }
                    }else{
                        this.$message.error('新密码不能为空！');
                    }
                }else{
                    this.$message.error('旧密码不能为空！');
                }
            },
            reset(){
                this.msgForm.nickName=this.user.user.userNickname;
                this.msgForm.tell=this.user.user.userTell;
                this.msgForm.sex=this.user.student.stuSex;
            },
            updata(){
                this.$axios({
                    method: "put",
                    url: "api/my/setMyMsg",
                    params: this.msgForm,
                }).then((result) => {
                    if (result.data == "ok") {
                        let user=JSON.parse(sessionStorage.getItem("user"));
                        user.userNickname=this.user.user.userNickname;
                        sessionStorage.setItem("user", JSON.stringify(user));
                        this.sel();
                        this.$message({
                            type: "success",
                            message: "修改成功！"
                        })
                    }else{
                        this.$message({
                            type: "error",
                            message: "修改失败！"
                        })
                    }
                })
            },
            sel(){
                this.$axios({
                    method: "get",
                    url: "api/my",
                    params:{
                        userId:JSON.parse(sessionStorage.getItem('user')).userId
                    }
                }).then((result) => {
                    this.user = result.data;
                    console.log(this.user)
                    this.msgForm.nickName=result.data.user.userNickname;
                    this.msgForm.tell=result.data.user.userTell;
                    this.msgForm.sex=result.data.student.stuSex;
                })
            }
        },
        created() {
            this.sel();
        },
        components: {
            userAvatar
        },
        data() {
            return {
                role:JSON.parse(sessionStorage.getItem('user')).roleId,
                user: {},
                msgForm: {
                    userId:JSON.parse(sessionStorage.getItem('user')).userId,
                    nickName: "",
                    tell: "",
                    sex: 0,
                },
                passwordForm: {
                    userId:JSON.parse(sessionStorage.getItem('user')).userId,
                    oldPassword: "",
                    newPassword: "",
                    renewlyPassword: "",
                },
                activeName: "用户资料",
            };
        },
    };
</script>

<style>
    .el-divider--horizontal {
        margin: 5px 0;
    }

    .myMessage {
        display: flex;
        justify-content: space-between;
        margin: 10px 5px;
        font-size: 13px;
    }

    .el-form-item {
        margin-bottom: 0px;
    }

    .myButton {
        margin: 20px 20px;
    }
</style>