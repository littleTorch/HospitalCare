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
                        <Span>{{user.emp.empName}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-location"/>员工类型
            </span>
                        <Span>{{empType}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-message"/>职务
            </span>
                        <Span>{{user.emp.duty}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-school"/>职称
            </span>
                        <Span>{{user.emp.possionalName}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-house"/>入职日期
            </span>
                        <Span>{{user.emp.entryDate}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" :class="user.emp.sex==1?'el-icon-male':'el-icon-female'"/>性别
            </span>
                        <Span>{{user.emp.sex==1?'男':'女'}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div class="myMessage">
            <span>
              <i style="font-size: 15px" class="el-icon-mobile-phone"/>手机号
            </span>
                        <Span>{{user.emp.phone}}</Span>
                    </div>
                    <el-divider></el-divider>
                    <div style="height: 20px"></div>
                </el-card>
            </el-col>
            <el-col :span="17">
                <el-card shadow="always">
                    <span>基本资料</span>aq
                    <el-divider></el-divider>
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="用户资料" name="用户资料">
                            <el-form :model="msgForm" label-position="right" label-width="80">
                                <el-form-item label="用户昵称">
                                    <el-input v-model="msgForm.empName" placeholder></el-input>
                                </el-form-item>
                                <el-form-item label="手机号">
                                    <el-input v-model="msgForm.phone" placeholder></el-input>
                                </el-form-item>
                                <el-form-item label="性别">
                                    <el-radio-group v-model="msgForm.sex">
                                        <el-radio :label="1">男</el-radio>
                                        <el-radio :label="0">女</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="简介">
                                    <el-input type="textarea" v-model="msgForm.brief" placeholder></el-input>
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
                this.msgForm.empName=this.user.emp.empName;
                this.msgForm.phone=this.user.emp.phone;
                this.msgForm.sex=this.user.emp.sex;
                this.msgForm.brief=this.user.emp.brief;
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
                    console.log(result.data)
                    this.user = result.data;
                    this.msgForm.empName=result.data.emp.empName;
                    this.msgForm.phone=result.data.emp.phone;
                    this.msgForm.sex=result.data.emp.sex;
                    this.msgForm.brief=result.data.emp.brief;
                    this.a();
                })
            },
            a(){
                let empType = this.user.emp.empType;
                // console.log(empType);
                //（0管理人员、1医务人员、2护理人员）
                switch (empType) {
                    case 0:
                        this.empType= "管理人员";
                        break;
                    case 1:
                        this.empType= "医务人员";
                        break;
                    case 2:
                        this.empType= "护理人员";
                        break;

                }
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
                empType:'',
                role:JSON.parse(sessionStorage.getItem('user')).roleId,
                user: {},
                msgForm: {
                    userId:JSON.parse(sessionStorage.getItem('user')).userId,
                    empName: "",
                    phone: "",
                    sex: 0,
                    brief:''
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