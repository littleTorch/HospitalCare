<template>
  <el-menu :default-active="$route.path" class="menu-bar" :collapse="isCollapse" unique-opened>
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