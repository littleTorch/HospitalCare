<template>
  <el-main>
    <el-form size="mini" :model="selectFrom" label-width="80px">
      <el-row>
        <el-col :span="4">
          <el-form-item label="名称:">
            <el-input v-model="selectFrom.label" placeholder="请输入权限名称"></el-input>
          </el-form-item>
        </el-col>
        <el-button
          @click="selectByLike(selectFrom.label)"
          style="margin-left: 20px;"
          size="mini"
          type="primary"
          icon="el-icon-search"
        >查询</el-button>
        <el-button @click="addMenu" size="mini" type="primary" icon="el-icon-plus">新增</el-button>
      </el-row>
    </el-form>
    <el-table
      :data="tableTreeDdata"
      :height="tableHeight"
      size="mini"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      :tree-props="{children: 'children'}"
    >
      <el-table-column prop="label" label="名称" sortable width="180"></el-table-column>
      <el-table-column prop="icon" label="图标" sortable width="180">
        <template slot-scope="scope">
          <i :class="scope.row.icon || ''"></i>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === '0'" size="small">目录</el-tag>
          <el-tag v-else-if="scope.row.type === '1'" size="small" type="success">菜单</el-tag>
          <el-tag v-else-if="scope.row.type === '2'" size="small" type="info">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="url" label="菜单URL"></el-table-column>
      <el-table-column prop="path" label="路由地址"></el-table-column>
      <el-table-column prop="code" label="权限标识"></el-table-column>
      <el-table-column prop="orderNum" label="序号"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="editMenu(scope.row)" size="mini" type="primary">编辑</el-button>
          <el-popconfirm
                  @onConfirm="handleDelete(scope.row.id)"
                  style="margin-left: 10px;"
                  title="您确定删除吗？"
                  icon="el-icon-info"
                  iconColor="red"
          >
            <el-button size="mini" type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增权限弹框 -->
    <el-dialog
            :title="boxTitle"
            :visible.sync="dialogVisible"
            width="40%"
    >
      <el-form :rules="rules" :inline="true" size="mini" :model="addForm" ref="addMenu" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="type">
              <el-radio-group v-model="addForm.type">
                <el-radio :label="'0'">目录</el-radio>
                <el-radio :label="'1'">菜单</el-radio>
                <el-radio :label="'2'">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="上级菜单">
          <el-input @click.native="selectParent" v-model="addForm.parentName"></el-input>
        </el-form-item>
        <el-form-item label="菜单名称" prop="label">
          <el-input v-model="addForm.label"></el-input>
        </el-form-item>
        <el-form-item v-if="addForm.type != '2'" label="菜单图标">
          <el-input v-model="addForm.icon"></el-input>
        </el-form-item>
        <el-form-item v-if="addForm.type == '1'" label="路由名称" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item v-if="addForm.type != '2'" label="路由地址" prop="path">
          <el-input v-model="addForm.path"></el-input>
        </el-form-item>
        <el-form-item v-if="addForm.type == '1'" label="组件路径" prop="url">
          <el-input v-model="addForm.url"></el-input>
        </el-form-item>
        <el-form-item label="权限标识" prop="code">
          <el-input v-model="addForm.code"></el-input>
        </el-form-item>
        <el-form-item label="显示序号" prop="orderNum">
          <el-input-number v-model="addForm.orderNum"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCommit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 选择上级菜单树弹框 -->
    <el-dialog class="el-dialog__wrapper" width="25%" title="上级菜单" :visible.sync="parentDialogVisible">
      <tree :nodes="nodes" :setting="parentSetting" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="parentDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="parentDialogVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>

<script>

  import tree from "vue-giant-tree";

export default {
  components: {
    tree
  },
  methods: {
    //选择上级菜单
    selectParent(){
      this.parentDialogVisible = true;
    },

    getCheckedNodes(){

    },

    //上级部门树点击事件
    ztreeParentOnClick(event, treeId, treeNode) {
      this.addForm.parentName = treeNode.name;
      this.addForm.parentId = treeNode.id;
      console.log(event);
      console.log(treeId);
      console.log(treeNode);
    },

    //模糊查询
    selectByLike(label) {
      this.$axios({
        method: "get",
        url: "api/permission/selectByLike",
        params: {
          label: label,
        },
      }).then((result) => {
        console.log(result.data.code)
        //刷新表格数据
        this.tableTreeDdata = result.data.data;
      });
    },

    addMenu(row){
      this.editTag = '0';
      this.addForm = {type:'0'};
      // this.addForm.type = '0'
      this.dialogVisible = true;
      this.boxTitle = "新增权限";
    },

    //点击编辑
    editMenu(row){
      // this.permissions = {type:'0'};
      //设置编辑标志为1
      this.editTag = "1";
      this.boxTitle = "编辑权限";
      console.log(row);
      //回显编辑的数据
      this.getPermissionById(row.id);
    },

    async getPermissionById(id) {
      console.log('id: '+id);
      let { data: res } = await this.$axios({
        url: 'api/permission/getPermissionById',
        params: {
          id: id
        }
      });
      console.log(res.code)
      if (res.code == 200) {
        console.log(res.data.label);
        console.log(res.data.type);
        this.addForm = res.data;
      }
      this.dialogVisible = true;
    },

    //提交新增权限
    async addCommit() {
      let _this = this;
      _this.$refs.addMenu.validate(async valid => {
        if (valid) {
          let url = "";
          let data = new FormData();
          if (_this.editTag == "0") {
            //新增
            url = "api/permission/addPermission";
          } else {
            url = "api/permission/editSave"; //编辑
            data.append('id',_this.addForm.id);
          }
          data.append('parentId',_this.addForm.parentId);
          data.append('parentName',_this.addForm.parentName);
          data.append('label',_this.addForm.label);
          data.append('code',_this.addForm.code);
          data.append('path',_this.addForm.path);
          data.append('name',_this.addForm.name);
          data.append('url',_this.addForm.url);
          data.append('orderNum',_this.addForm.orderNum);
          data.append('type',_this.addForm.type);
          data.append('remark',_this.addForm.remark);
          let { data: res } = await _this.$axios({
            method: 'post',
            url: url,
            data: data
          });
          console.log("addCommit::"+res.code)
          if (res.code == 200) {
            _this.$message({
              message: res.msg,
              type: 'success'
            });
            //关闭弹框
            _this.dialogVisible = false;
            //刷新表格数据
            _this.getMenuList();
            //刷新上级树
            _this.getParentTree();
          } else {
            _this.$message({
              message: res.msg,
              type: 'error'
            });
          }
        }
      });
    },

    //删除权限
    async handleDelete(id) {
      let { data: res } = await this.$axios({
        url: "api/permission/deleteById",
        params: {
          id: id
        }
      });
      if (res.code == 200) {
        this.$message({
          message: res.msg,
          type: "success"
        });
        //刷新表格数据
        this.getMenuList();
        //刷新上级树
        this.getParentTree();
      } else {
        this.$message({
          message: res.msg,
          type: "error"
        });
      }

    },

    //获取权限列表
    async getMenuList() {
      let { data: res } = await this.$axios.post("/api/permission/getMenuList");
      this.tableTreeDdata = res.data;
    },

    // 获取上级菜单树数据
    async getParentTree() {
      let { data: res } = await this.$axios.post("api/permission/getParentTree");
      console.log(res);
      this.nodes = res.data;
    }

  },
  data() {
    return {
      //表单验证
      rules: {
        parentName: [
          {required: true, message: "上级菜单不能为空", trigger: "blur"},
        ],
        label: [
          {required: true, message: "菜单名称不能为空", trigger: "blur"},
        ],
        type: [
          {required: true, message: "菜单类型不能为空", trigger: "blur"},
        ],
        name: [
          {required: true, message: "路由名称不能为空", trigger: "blur"},
        ],
        path: [
          {required: true, message: "路由地址不能为空", trigger: "blur"},
        ],
        url: [
          {required: true, message: "组件路径不能为空", trigger: "blur"},
        ],
        code: [
          {required: true, message: "权限标识不能为空", trigger: "blur"},
        ],
        orderNum: [
          {required: true, message: "显示序号不能为空", trigger: "blur"},
        ],
      },
      editTag: '0',
      boxTitle: '',
      innerVisible: false,
      //控制上级部门弹框显示
      parentDialogVisible: false,
      //上级树陪
      parentZtreeObj: null,
      parentNodes: [], //上级部门树数据
      //上级部门树配置
      parentSetting: {
        view: {
          showLine: true,
          showIcon: false,
          fontCss: { "font-size": "12px", color: "#333" }
        },
        //设置这里会显示复选框
        // check: {
        // enable: true
        // },
        data: {
          simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "0"
          }
        },
        callback: {
          onClick: this.ztreeParentOnClick
        }
      },

      nodes: [],

      //新增权限弹框
      dialogVisible: false,
      tableHeight: 0,
      selectHistoryFrom: {
        hisStuName: "",
        hisStuNo: "",
        hisRoom: "",
        stuPapersNo: "",
      },
      addForm: [],
      permissions: [],
      selectFrom: {label: ''},
      //表格数据
      tableTreeDdata: [],
    };
  },
  created() {
    this.getMenuList();
    this.getParentTree();
    // this.getParentList();
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 280;
    });
  },
};
</script>

<style>
  .el-dialog__wrapper /deep/ .el-dialog__body {
    padding-top: 5px !important;
  }
</style>