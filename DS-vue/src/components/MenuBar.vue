<template>
  <el-menu background-color="#545c64"
           text-color="#fff"
           active-text-color="#ffd04b"
           :default-active="$route.path"
           class="menu-bar"
           :collapse="isCollapse"
           unique-opened>
    <el-menu-item index="2">
      <i>
        <el-avatar src="https://gss3.bdstatic.com/84oSdTum2Q5BphGlnYG/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=3d64b4492c4231c8bfa8b8bb4e482b38&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Ff11f3a292df5e0fe612723ba5d6034a85edf724a.jpg"></el-avatar>
      </i>
      <span slot="title" style="margin-left: 10px">后台管理系统</span>
    </el-menu-item>
    <menu-item :menuList="menuList"></menu-item>
  </el-menu>
</template>

<script>
import MenuItem from "./MenuItem";
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState({
      //获取vuex中的菜单数据
      menuList:state=>state.MenuStore.menu_data,
      // 获取vue中的菜单展开收缩属性
      isCollapse:state=>state.MenuStore.isCollapse
    })
  },
  name: "MenuBar",
  components: {
    MenuItem
  },
};
</script>

<style>
.menu-bar:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-ment {
  border-right: none;
}
.el-aside {
  border-right: 1px solid #ccc;
}
</style>