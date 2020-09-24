<template>
  <el-main>
    <el-form size="mini" :model="selectRoleFrom" label-width="80px">
      <el-row>
        <el-col :span="4">
          <el-form-item label="角色名称:">
            <el-input v-model="selectRoleFrom.name" placeholder="请输入角色名称"></el-input>
          </el-form-item>
        </el-col>
        <el-button
                @click="selectByLike(selectRoleFrom.name)"
                style="margin-left: 20px;"
                size="mini"
                type="primary"
                icon="el-icon-search"
        >查询
        </el-button>
        <el-button
                @click="addRoleVisible = true"
                size="mini"
                type="primary"
                icon="el-icon-plus"
        >新增
        </el-button>
      </el-row>
    </el-form>
    <el-table
            ref="roleTable"
            :data="roleTable"
            tooltip-effect="dark"
            style="width: 100%"
            size="mini"
            :height="tableHeight"
            border
    >
      <el-table-column prop="name" label="角色名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作" align="center" width="250">
        <template slot-scope="scope">
          <el-button @click="editRole(scope.row)" size="mini" type="primary">编辑</el-button>
          <el-button @click="assignRole(scope.row)" size="mini" type="success">分配权限</el-button>
          <el-popconfirm
                  @onConfirm="deleteOne(scope.row.id)"
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

    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="page.currentPage"
            :page-size="page.pageSize"
            :total="page.totalCount"
            layout="total, prev, pager, next"
    ></el-pagination>

    <el-dialog title="新增角色" :visible.sync="addRoleVisible" width="40%">
      <el-form
              size="mini"
              ref="addRole"
              style="text-align:center"
              :model="addRole"
              :inline="true"
              label-width="100px"
              status-icon
              :rules="rules"
      >
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="addRole.name" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="addRole.remark" placeholder="请输入备注信息"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addRoleVisible = false">取 消</el-button>
        <el-button @click="addOneRole" type="primary">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="编辑角色" :visible.sync="updateOneRoleVisible" width="40%">
      <el-form
              size="mini"
              ref="updateRole"
              style="text-align:center"
              :model="updateData"
              :inline="true"
              label-width="100px"
              status-icon
              :rules="rules"
      >
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="updateData.name" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="updateData.remark" placeholder="请输入备注信息"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneRoleVisible = false">取 消</el-button>
        <el-button @click="editOneRole()" type="primary">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog class="self_dialog" :title="dialogTitle" :visible.sync="innerVisible" width="25%">
      <tree
              :nodes="treeDatas"
              :setting="setting"
              @onCheck="ztreeOnCheck"
              @onCreated="handleCreated"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="innerVisible = false">取 消</el-button>
        <el-button @click="saveAssign" type="primary">确 定</el-button>
      </span>
    </el-dialog>
  </el-main>
</template>

<script>
  import tree from "vue-giant-tree";
  import qs from "qs";

  export default {
    components: {
      tree,
    },
    methods: {
      //模糊查询
      selectByLike(name) {
        this.$axios({
          method: "get",
          url: "api/role/selectByLike",
          params: {
            name: name,
          },
        }).then((result) => {
          this.roleTable = result.data.data;
        });
      },

      addOneRole() {
        this.$refs.addRole.validate((valid) => {
          if (valid) {
            this.$axios.post("api/role/addRole", qs.stringify(this.addRole)).then((result) => {
              if (result.data.code == 200) {
                this.$message({
                  type: "success",
                  message: result.data.msg,
                });
                this.addRoleVisible = false;
                this.$refs.addRole.resetFields(); // 刷新表单
                let num = parseInt(this.page.totalCount/this.page.pageSize)+1;
                this.getTableData(num);
              } else {
                this.$message({
                  type: "error",
                  message: result.data.msg,
                });
              }
            });
          }
        });
      },

      //编辑
      editRole(row) {
        this.updateOneRoleVisible = true;
        this.updateData = row;
      },

      editOneRole() {
        this.$refs.updateRole.validate((valid) => {
          if (valid) {
            this.$axios.post("api/role/updateRole", qs.stringify(this.updateData)).then((result) => {
              if (result.data.code == 200) {
                this.$message({
                  type: "success",
                  duration: 1000,
                  message: result.data.msg,
                });
                this.updateOneRoleVisible = false;
                let num = parseInt(this.page.totalCount/this.page.pageSize)+1;
                this.getTableData(num);
              } else {
                this.$message({
                  type: "error",
                  duration: 1000,
                  message: result.data.msg,
                });
              }
            });
          }
        });
      },

      //分配权限
      assignRole(row) {
        this.dialogTitle = '为【'+row.name+'】分配权限';
        this.roldId = row.id;
        console.log('userId: '+this.userId);
        console.log('roleId: '+this.roldId);
        let _this = this;
        this.treeDatas = [];
        this.$axios('api/role/permissionTree',{
          params: {
            userId: _this.userId,
            roleId: _this.roldId
          }
        }).then(res => {
          if (res.data.code == 200) {
            this.treeDatas = res.data.data;
          }
        });
        this.innerVisible = true;
      },

      //保存分配的权限
      async saveAssign() {
        if (this.checkPermissions.length < 1) {
          this.$message({
            message: "请勾选权限!",
            type: "success"
          });
          return;
        }
        let parms = {
          list: this.checkPermissions,
          roleId: this.roldId
        }
        let { data: res } = await
            this.$axios.post("/api/role/saveAssignRole",parms);
        if(res.code ==200){
          this.innerVisible = false;
          this.$message({
            message:res.msg,
            type:'success'
          })
        }else{
          this.$message({
            message:res.msg,
            type:'error'
          })
        }
      },

      //获取选择的权限
      ztreeOnCheck() {
        let checked = this.ztreeObj.getCheckedNodes(true);
        this.checkPermissions = checked;
        console.log(checked);
      },

      handleCreated: function (ztreeObj) {
        console.log("加载树完成");
        this.ztreeObj = ztreeObj;
        // this.ztreeObj.checkAllNodes(true);
        console.log(this.ztreeObj);
        this.ztreeObj.expandAll(true);
      },

      //删除
      deleteOne(id) {
        this.$axios({
          method: "get",
          url: "api/role/deleteOne",
          params: {
            id: id,
          },
        }).then((result) => {
          if (result.data.code == 200) {
            this.$message({
              type: "success",
              duration: 1000,
              message: result.data.msg,
            });
            if (((this.page.totalCount-1)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
              this.getTableData(this.page.currentPage-1);
            } else {
              this.getTableData(this.page.currentPage);
            }
          } else {
            this.$message({
              type: "error",
              duration: 1000,
              message: result.data.msg,
            });
          }
        });
      },

      //复选框事件
      handleSelectionChange(val) {
        this.tableChecked = val;
      },

      //pageSize改变调用
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      getTableData(pageNo) {
        let self = this
        self.$axios.get('api/role/getRoleList', {
          params: {
            pageSize: 10,
            currentPage: pageNo
          }
        }).then(res => {
          if (res.data.code == 200) {
            self.roleTable = res.data.data.records;
            self.$set(self.page, 'totalCount', res.data.data.total)
            self.$set(self.page, 'pageSize', res.data.data.size)
            self.$set(self.page, 'currentPage', res.data.data.current)
          }
        });
      },
      // 页数改变调用
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.getTableData(val);
      },
    },

    data() {
      return {
        //表单验证
        rules: {
          name: [
            {required: true, message: "角色名称不能为空", trigger: "blur"},
          ],
          remark: [
            {required: true, message: "角色说明不能为空", trigger: "blur"},
          ],
        },

        //ztree对象
        ztreeObj: null,
        //ztree配置，参照ztree官网的配置
        setting: {
          check: {
            //树是否带复选框或者单选框
            enable: true,
          },
          data: {
            simpleData: {
              //是否使用简单数据模式
              enable: true,
              //树节点id，一般是后台数据库主键
              idKey: "id",
              //父级id
              pIdKey: "pid",
              rootPId: "0",
            },
          },
          //ztree回调函数
          callback: {
            //树选择事件
            onCheck: this.ztreeOnCheck,
          },
        },
        innerVisible: false, //控制弹框显示
        checkPermissions: [],
        treeDatas: [],
        roldId: '',

        page: {
          currentPage: 1, // 当前页
          pageSize: 10, // 每页显示条目个数
          totalCount: 0 // 总条目数
        },

        pageSize: 10,
        currentPage: 1,
        total: 0,
        userId: '',
        addRoleVisible: false,
        addRole: [],
        updateData: [],
        updateOneRoleVisible: false,
        tableChecked: [],
        tableHeight: 0,
        selectRoleFrom: {
          name: "",
        },
        //表格数据
        roleTable: [],
        dialogTitle: '',
      };
    },

    created() {
      this.userId = JSON.parse(sessionStorage.getItem("user")).userId;
      this.getTableData(1);
    },

    mounted() {
      this.$nextTick(() => {
        this.tableHeight = window.innerHeight - 280;
      });
    },
  };
</script>

<style lang="scss" scoped>
  .self_dialog {
    display: flex;
    justify-content: center;
    align-items: Center;
    overflow: hidden;
  }

  .self_dialog /deep/ .el-dialog {
    margin: 0 auto !important;
    height: 90%;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    padding-left: 15px;
  }

  .self_dialog /deep/ .el-dialog .el-dialog__body {
    padding-top: 5px !important;
    overflow: hidden;
    overflow-y: auto;
    margin-bottom: 40px;
  }

  .self_dialog /deep/ .el-dialog .el-dialog__footer {
    left: 40%;
    bottom: 0;
    position: absolute;
  }
</style>