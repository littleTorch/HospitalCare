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
                    <div class="login-title">广东理工职业颐养院管理系统</div>
                </el-form-item>

                <el-form-item label=""  prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>

                <el-form-item label=""  prop="password">
                    <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" show-password></el-input>
                </el-form-item>

                <el-form-item label prop="code">
                    <div class="captcha_model">
            
                      <div class="content">
                          <div class="sliding-pictures">
                              <i class="iconfont icon-shuaxin" @click="onRefresh" title="刷新验证码"></i>
                              <div id="captcha">

                              </div>
                          </div>
                      </div>
                      <div class="sliderContainer">
                          <div class="sliderMask">
                              <div class="slider">
                                  <span class="sliderIcon"></span>
                              </div>
                          </div>
                          <span class="sliderText">向右滑动填充拼图</span>
                      </div>

                  </div>
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
        <el-col :span="8" class="login-container">
        </el-col>
    </el-row>


</template>

<script>

    import  '../scss/captcha.scss'

    export default {
        data() {
            return {
                visible: true,
                cv:{
                    w:310,
                    h:80,
                },
                block:{
                    l:30, // 滑块边长
                    L:42+9*2+3,// 滑块实际边长
                    r:5, // 滑块半径
                },
                randomPos:{
                    x:0,
                    y:0,
                },
                code: true,
                canvasCtx:null,
                blockCtx:null,
                blockDom:null,
                sliderDom:null,
                sliderContainerDom:null,
                sliderMaskDom:null,
                el:null,
                img:null,
                trail:null,
               
                // imageUrl: 'http://localhost:8081/login/image?t=' + new Date().getTime(),
                backgroundDiv: {
                    backgroundImage: "url(" + require("@/assets/images/bg.png") + ")",
                    backgroundRepeat: "no-repeat",
                    backgroundSize: "100% 100%",
                },
                loginForm: {
                    username: "",
                    password: "",
                },
                dialogVisible : false,
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
                    // code: {
                    //     required: true,
                    //     trigger: "change",
                    //     message: "请滑动验证",
                    // },
                },
            };
        
        },
        watch: { },
        beforeCreate() {},
        created() {},
        beforeMount() {},
        mounted() {
            this.init();
        },    
        methods: {
            // renewalImage() {
            //     this.imageUrl = 'http://localhost:8081/login/image?t=' + new Date().getTime();
            // },
            submit() {
                this.$refs.loginForm.validate((valid) => {
                    if(this.code){
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
                                    // let routerList = [];
                                    // let chi = [];
                                    // for (let i = 0; i < menuList.length; i++) {
                                    //     chi.push(menuList[i].children);
                                    // }
                                    // for (let i = 0; i < chi.length; i++) {
                                    //     for (let j = 0; j < chi[i].length; j++) {
                                    //         if (chi[i][j].children){
                                    //             for (let k=0;k<chi[i][j].children.length;k++){
                                    //                 routerList.push(chi[i][j].children[k]);
                                    //             }
                                    //         }
                                    //         routerList.push(chi[i][j]);
                                    //     }
                                    // }
                                    let user=result.data.data;
                                    console.log(user);
                                    //存储用户信息
                                    sessionStorage.setItem("user", JSON.stringify(user));
                                    sessionStorage.setItem("token", result.data.data.token);
                                    //存储菜单数据
                                    sessionStorage.setItem("menuList", JSON.stringify(menuList));
                                    //存储路由数据
                                    sessionStorage.setItem("routerList", JSON.stringify(user.routerList));
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
                    }else{
                        this.$message({
                            showClose: true,
                            message: '请拖动验证码到指定位置完成验证！',
                            type: 'error'
                        });
                    }
                });
            },
            close(){
                this.visible = false
            },
            init(){
                this.canvasInit();
                this.initImg();
                this.bindEvents();
            },
            onRefresh(){
                this.reset()
            },
            onSuccess(){
                // this.$msg.success("登录成功！");
                this.code=true;
                console.log(this.code)
            },
            onFail(){
                // this.$msg.warning("再试一次！");
                this.code=false;
                console.log(this.code)
            },
            bindEvents () {
                this.el.onselectstart = () => false

                let originX, originY, trail = [], isMouseDown = false

                const addClass = function (tag, className) {
                    tag.classList.add(className)
                }

                const removeClass = function  (tag, className) {
                    tag.classList.remove(className)
                }


                const handleDragStart = function (e) {
                    originX = e.clientX || e.touches[0].clientX
                    originY = e.clientY || e.touches[0].clientY
                    isMouseDown = true
                    console.log(originX)
                }

                const handleDragMove = (e) => {
                    if (!isMouseDown) return false
                    const eventX = e.clientX || e.touches[0].clientX
                    const eventY = e.clientY || e.touches[0].clientY
                    const moveX = eventX - originX
                    const moveY = eventY - originY
                    if (moveX < 0 || moveX + 38 >= this.cv.w) return false
                    this.sliderDom.style.left = moveX + 'px'
                    const blockLeft = (this.cv.w - 40 - 20) / (this.cv.w - 40) * moveX
                    this.blockDom.style.left = blockLeft + 'px'

                    addClass(this.sliderContainerDom, 'sliderContainer_active')
                    this.sliderMaskDom.style.width = moveX + 'px'
                    trail.push(moveY)
                }

                const handleDragEnd = (e) => {
                    if (!isMouseDown) return false
                    isMouseDown = false
                    const eventX = e.clientX || e.changedTouches[0].clientX
                    if (eventX == originX) return false
                    removeClass(this.sliderContainerDom, 'sliderContainer_active')
                    this.trail = trail
                    const { spliced, verified } = this.verify()
                    if (spliced) {
                        if (verified) {
                           addClass(this.sliderContainerDom, 'sliderContainer_success')
                           
                           this.onSuccess();
                        } else {
                            addClass(this.sliderContainerDom, 'sliderContainer_fail')
                            this.onFail();
                            this.reset()
                        }
                    } else {
                        addClass(this.sliderContainerDom, 'sliderContainer_fail')
                        this.onFail();
                        setTimeout(() => {
                            this.reset()
                        }, 1000)
                    }
                }
                this.sliderDom.addEventListener('mousedown', handleDragStart)
                this.sliderDom.addEventListener('touchstart', handleDragStart)
                this.blockDom.addEventListener('mousedown', handleDragStart)
                this.blockDom.addEventListener('touchstart', handleDragStart)
                document.addEventListener('mousemove', handleDragMove)
                document.addEventListener('touchmove', handleDragMove)
                document.addEventListener('mouseup', handleDragEnd)
                document.addEventListener('touchend', handleDragEnd)
            },
            verify () {
                const sum = function (x, y) {
                    return x + y
                }
                const square =  function  (x) {
                    return x * x
                }

                const arr = this.trail // 拖动时y轴的移动距离
                const average = arr.reduce(sum) / arr.length
                const deviations = arr.map(x => x - average)
                const stddev = Math.sqrt(deviations.map(square).reduce(sum) / arr.length)
                const left = parseInt(this.blockDom.style.left)
                return {
                    spliced: Math.abs(left - this.randomPos.x) < 10,
                    verified: stddev !== 0, // 简单验证下拖动轨迹，为零时表示Y轴上下没有波动，可能非人为操作
                }
            },
            reset () {
                this.sliderContainerDom.className = 'sliderContainer'
                this.sliderDom.style.left = 0
                this.blockDom.style.left = 0
                this.sliderMaskDom.style.width = 0
                this.clean()
                this.img.setSrc(this.getRandomImgSrc())
            },
            clean () {
                this.canvasCtx.clearRect(0, 0, this.cv.w, this.cv.h)
                this.blockCtx.clearRect(0, 0, this.cv.w, this.cv.h)
                this.blockDom.width = this.cv.w
            },
            draw () {
                // 随机创建滑块的位置
                this.randomPos.x = this.getRandomNumberByRange(this.block.L + 10, this.cv.w - (this.block.L + 10))
                this.randomPos.y = this.getRandomNumberByRange(10 + this.block.r * 2, this.cv.h - (this.block.L + 10))
                this.drawBlock(this.canvasCtx, this.randomPos.x, this.randomPos.y, 'fill')
                this.drawBlock(this.blockCtx, this.randomPos.x, this.randomPos.y, 'clip')
            },
            drawBlock(ctx, x, y, operation) {
                let r = this.block.r,
                    l = this.block.l,
                    PI = Math.PI;

                ctx.beginPath()
                ctx.moveTo(x, y)
                ctx.arc(x + l / 2, y - r + 2, r, 0.72 * PI, 2.26 * PI)
                ctx.lineTo(x + l, y)
                ctx.arc(x + l + r - 2, y + l / 2, r, 1.21 * PI, 2.78 * PI)
                ctx.lineTo(x + l, y + l)
                ctx.lineTo(x, y + l)
                ctx.arc(x + r - 2, y + l / 2, r + 0.4, 2.76 * PI, 1.24 * PI, true)
                ctx.lineTo(x, y)
                ctx.lineWidth = 2
                ctx.fillStyle = 'rgba(255, 255, 255, 0.7)'
                ctx.strokeStyle = 'rgba(255, 255, 255, 0.7)'
                ctx.stroke()
                ctx[operation]()
                ctx.globalCompositeOperation = 'destination-over'
            },
            initImg(){
                const img = this.createImg(() => {
                    this.draw()
                    this.canvasCtx.drawImage(img, 0, 0, this.cv.w, this.cv.h)
                    this.blockCtx.drawImage(img, 0, 0, this.cv.w, this.cv.h)
                    const y = this.randomPos.y - this.block.r * 2 - 1;
                    const ImageData = this.blockCtx.getImageData(this.randomPos.x - 3, y, this.block.L, this.block.L)
                    this.blockDom.width = this.block.L
                    this.blockCtx.putImageData(ImageData, 0, y)
                })
                this.img = img
            },
            canvasInit(){
                
                const canvas = this.createCanvas(this.cv.w, this.cv.h) // 画布
                const block = canvas.cloneNode(true) // 滑块

                block.className = 'block'
                const el = this.el = document.getElementById('captcha');console.log(document.getElementById('captcha'));
                el.style.position = 'relative'
                el.style.width = this.cv.w + 'px'
                Object.assign(el.style, {
                    position: 'relative',
                    width: this.cv.w + 'px',
                    margin: '0 auto'
                })
                
                this.el = el
                el.appendChild(canvas)
                el.appendChild(block)
                
                this.canvasCtx = canvas.getContext("2d");
                this.blockCtx = block.getContext("2d");
                this.blockDom=block;
                this.sliderDom=document.getElementsByClassName('slider')[0];
                this.sliderContainerDom=document.getElementsByClassName('sliderContainer')[0];
                this.sliderMaskDom=document.getElementsByClassName('sliderMask')[0];
                console.log(this.sliderDom)
            },
            createCanvas (width, height) {
                const canvas = document.createElement('canvas')
                canvas.width = width
                canvas.height = height
                return canvas
            },
            createImg (onload) {
                const img = new Image()
                img.crossOrigin = "Anonymous"
                img.onload = onload
                img.onerror = () => {
                    img.setSrc(this.getRandomImgSrc())
                }

                img.setSrc =  (src) =>{
                    if (window.navigator.userAgent.indexOf('Trident') > -1) { // IE浏览器无法通过img.crossOrigin跨域，使用ajax获取图片blob然后转为dataURL显示
                        const xhr = new XMLHttpRequest()
                        xhr.onloadend =  (e) =>{
                            const file = new FileReader() // FileReader仅支持IE10+
                            file.readAsDataURL(e.target.response)
                            file.onloadend = function (e) {
                                img.src = e.target.result
                            }
                        }
                        xhr.open('GET', src)
                        xhr.responseType = 'blob'
                        xhr.send()
                    }
                    else img.src = src
                }
                img.setSrc(this.getRandomImgSrc())
                return img
            },

            getRandomNumberByRange (start, end) {
                return Math.round(Math.random() * (end - start) + start)
            },
            getRandomImgSrc () {
                return 'https://picsum.photos/300/150/?image=' + this.getRandomNumberByRange(0, 1084)
                // return 'https://picsum.photos/id/407/300/150'
            },
        }
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
        height: 400px;
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
    .el-form-item.is-required:not(.is-no-asterisk)>.el-form-item__label:before{
          content:"";
    }
</style>