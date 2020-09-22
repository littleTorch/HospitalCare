<template>
  <el-container class="home">
    <el-container>
      <!--菜单栏-->
      <el-aside width="auto" style="background: #545c64">
        <menu-bar></menu-bar>
      </el-aside>
      <!--菜单栏-->
      <el-container>
    <!--头部-->
    <el-header class="header ub main-justify cross-center ">
      <div class="header-left-text">广东理工职业颐养院系统</div>
      <div class="ub main-center cross-center">
      <el-dropdown placement="bottom-start">
        <el-avatar class="user-img" :size="50" :src="circleUrl"></el-avatar>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="my">个人中心</el-dropdown-item>
          <el-dropdown-item @click.native="logout">注销</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
        <div class="header-right-text">
          <div class="wollcom">欢迎您，{{username}}</div>
          <div class="header-time">{{nowDate}}</div>
        </div>
      </div>
    </el-header>
<!--头部-->

        <el-main style="padding:0px">
        <!--菜单收缩按钮-->
        <i class="arrowIcon" :class="[isCollapse ? 'el-icon-s-unfold' : ' el-icon-s-fold']" @click="iconClick"></i>
          <tabs></tabs>
          <router-view></router-view>
        </el-main>
        <el-footer class="foot" height="35px">广东理工职业颐养院</el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import MenuBar from '../components/MenuBar.vue'
import tabs from '../components/tabs.vue'
import {mapState} from 'vuex'
export default {
  name: 'Home',
  components: {
    MenuBar,
    tabs
  },
  created() {
    this.username=JSON.parse(sessionStorage.getItem("user")).userName;
    let date=new Date();
    this.nowDate=date.getFullYear()+"年"+(1+date.getMonth())+"月"+date.getDate()+"日";
  },
  computed:{
    ...mapState({
      isCollapse:state=>state.MenuStore.isCollapse
    })
  },
  data () {
      return {
        nowDate:'',
        username:'',
        circleUrl: 'http://localhost:8282/my/icon?userId='+JSON.parse(sessionStorage.getItem("user")).userId,
      }
    },
    methods:{
    logout(){
      sessionStorage.clear();
      this.$router.push({name:'login'});
    },
    my(){
      console.log(JSON.parse(sessionStorage.getItem("user")));
      if(JSON.parse(sessionStorage.getItem("user")).userNickname!="root"){
        let obj={
          name:'my',
          label:'个人中心'
        }
        this.$store.commit('selectMenu',obj);
        this.$router.push({name:'my'});
      }else{
        this.$message({
          message: '该账号无个人中心哦！',
          type: 'warning'
        });
      }
    },
      iconClick(){
        console.log('点击了')
        this.$store.commit('setOpenOrClose');
      }
    }
  }
</script>
<style lang="css" scope>
.el-tabs__header{
  position: static !important;
}
.arrowIcon{
  font-size: 24px;
  border: 1px solid transparent;
  float: left;
  height: 39px;
  width: 50px;
  background-color: #eaedf1;
  text-align: center;
  line-height: 39px!important;
}
.home{
  height: 100%;
}
.header{
  background-color: #545c64;
  color: #fff;
  justify-content: space-between;
}
.header-left-text{
  font-size: 20px;
}
.header-right-text{
  margin-left: 10px;
}
.user-img{
  cursor: pointer;
}
.wollcom{
  font-size: 15px;
  font-weight: bolder;
}
.header-time{
  font-size: 14px;
}
.foot{
  background: #545c64;
  line-height: 35px;
  color: #fff;
  font-size: 12px;
}
</style>