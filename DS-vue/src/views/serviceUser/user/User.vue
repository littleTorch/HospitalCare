<template>
  <el-container>
    <el-main>
      <el-form size="mini" :model="searchForm" ref="searchForm" label-width="80px">
        <el-row>
          <el-col :span="4">
            <el-form-item label="用户名">
              <el-input v-model="searchForm.userName" placeholder="请输入用户名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="联系方式">
              <el-input v-model="searchForm.userTell" placeholder="请输入联系方式"></el-input>
            </el-form-item>
          </el-col>
          <el-button @click="selectByLike" style="margin-left:20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
          <el-button @click="addUserVisible = true" size="mini" type="primary" icon="el-icon-plus">新增</el-button>
        </el-row>
      </el-form>
      <el-table
        size="mini"
        ref="tableData"
        :data="tableData"
        :height="tableHeight"
        border
        style="width: 100%"
      >
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column :formatter="sexFTR" prop="sex" label="性别"></el-table-column>
        <el-table-column prop="userRoot" label="权限等级"></el-table-column>
        <el-table-column prop="userTell" label="联系方式"></el-table-column>
        <el-table-column prop="userIcon" label="头像"></el-table-column>
        <el-table-column prop="userNickname" label="昵称"></el-table-column>
        <el-table-column label="操作" align="center" width="250">
          <template slot-scope="scope">
            <el-button @click="editUser(scope.row)" size="mini" type="primary">编辑</el-button>
            <el-button @click="assignRole(scope.row)" size="mini" type="success">分配角色</el-button>
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

      <el-dialog title="新增用户" :visible.sync="addUserVisible" width="30%">
        <el-form :inline="true" :rules="rules" size="mini" :model="addForm" ref="addForm" label-width="80px">
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="addForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="addForm.sex">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="昵称" prop="userNickname">
            <el-input v-model="addForm.userNickname"></el-input>
          </el-form-item>
          <el-form-item label="联系方式" prop="userTell">
            <el-input v-model="addForm.userTell"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="userPassword">
            <el-input v-model="addForm.userPassword"></el-input>
          </el-form-item>
          <el-form-item label="权限等级" prop="userRoot">
            <el-input v-model="addForm.userRoot"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addUserVisible = false">取 消</el-button>
          <el-button type="primary" @click="addUser">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog title="编辑用户" :visible.sync="updateUserVisible" width="30%">
        <el-form :inline="true" size="mini" :model="updateForm" ref="updateForm" label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="updateForm.username"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="updateForm.sex">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="updateForm.userNickname"></el-input>
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="updateForm.userTell"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="updateForm.userPassword" disabled></el-input>
          </el-form-item>
          <el-form-item label="权限等级">
            <el-input v-model="updateForm.userRoot"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="updateUserVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateUser">确 定</el-button>
        </span>
      </el-dialog>

      <!--分配角色-->
      <el-dialog class="self_dialog roleClass" title="分配角色" :visible.sync="roleDialogVisible" width="25%">
        <el-table
          @current-change="selectRoleRow"
          highlight-current-row
          :data="roleTableData"
          style="width: 100%"
          ref="roleTable"
        >
          <el-table-column prop="id" label="序号" width="180"></el-table-column>
          <el-table-column prop="name" label="名称"></el-table-column>
        </el-table>
        <div></div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="roleDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmSetRole">确 定</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>

  import qs from 'qs';

export default {
  methods: {
    sexFTR(row){
      if(row.sex=="1"){
        return "男"
      }else{
        return "女"
      }
    },

    //模糊查询
    selectByLike() {
      this.$axios({
        method: "get",
        url: "api/user/selectByLike",
        params: {
          userName: this.searchForm.userName,
          userTell: this.searchForm.userTell
        },
      }).then((result) => {
        this.tableData = result.data.data;
      });
    },

    //确认新增用户
    addUser() {
      this.$refs.addForm.validate(valid => {
        if (valid){
          this.$axios.post('api/user/addUser', qs.stringify(this.addForm)).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                message: result.data.msg,
              });
              this.addUserVisible = false;
              this.$refs.addForm.resetFields(); // 刷新表单
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
      })
    },

    //点击编辑
    editUser(row) {
      this.updateUserVisible = true;
      this.updateForm = row;
    },

    //确认编辑用户
    updateUser(){
      this.$refs.updateForm.validate(valid => {
        if (valid){
          this.$axios.post('api/user/updateSaveUser', qs.stringify(this.updateForm)).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.updateUserVisible = false;
              this.getTableData(this.page.currentPage);
            } else {
              this.$message({
                type: "error",
                duration: 1000,
                message: result.data.msg,
              });
            }
          });
        }
      })
    },

    //选中角色
    selectRoleRow(row) {
      this.currentRow = row;
      console.log(row);
    },

    //点击分配角色按钮
    async assignRole(row) {
      // this.$refs.roleTable.setCurrentRow();
      this.selectUserId = row.id;
      this.roleId = 0;
      console.log("selectUserId: "+row.id)
      // 分配角色获取角色列表
      await this.$axios({
        method: 'post',
        url: 'api/role/getRoleListForAssing'
      }).then(res => {
        if(res.data.code == 200){
          this.roleTableData = res.data.data;
        }
      });

      //查询用户拥有的角色
      await this.$axios.get('api/role/getRoleIdByUserId', {
        params: {
          userId: row.id
        }
      }).then(res => {
        if (res.data.code == 200){
          if (res.data.data){
            // console.log(res.data.data)
            console.log(res.data)
            console.log('res.data.data.roleId: '+res.data.data.roleId)
            this.roleId = res.data.data.roleId;
          }
        }
      });

      let _this = this;
      _this.$nextTick(function (){
        _this.$refs.roleTable.setCurrentRow();
        console.log('roleTableData[0].id: '+_this.roleTableData[0].id)
        console.log('roleId: '+_this.roleId);
        console.log('roleId == roleTableData[0].id? '+(Number(_this.roleId) == Number(_this.roleTableData[0].id)))
        for (let i = 0; i < _this.roleTableData.length; i++) {
          if(Number(_this.roleId)==_this.roleTableData[i].id){
            _this.$refs.roleTable.setCurrentRow(_this.roleTableData[i]);
            _this.currentRow=_this.roleTableData[i];
          }
        }
      })
      if (this.first){
        this.first = false;
        this.roleDialogVisible = true;
        this.assignRole(row);
      } else {
        this.roleDialogVisible = true;
      }
    },

    //分配角色确认按钮
    confirmSetRole() {
      this.$axios({
        method: 'post',
        url: 'api/role/assignRole',
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        },
        data: {
          userId: this.selectUserId,
          roleId: this.currentRow.id
        }
      }).then((result) => {
        if (result.data.code == 200) {
          this.$message({
            type: "success",
            message: result.data.msg,
          });
          this.roleDialogVisible = false;
        } else {
          this.$message({
            type: "error",
            message: result.data.msg,
          });
        }
      });
      this.roleDialogVisible = false;
    },

    //删除角色
    deleteOne(userId) {
      if(1==userId) {
        this.$message({
          type: 'error',
          message: '不能删除超级管理员'
        })
        return;
      }
      if(userId==this.userId){
        this.$message({
          type: 'error',
          message: '不能删除当前用户'
        })
        return;
      }
      this.$axios({
        method: 'get',
        url: 'api/user/deleteUserById',
        params: {
          userId: userId
        }
      }).then((result) => {
        if (result.data.code == 200) {
          this.$message({
            type: "success",
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
            message: result.data.msg,
          });
        }
      });
    },

    //pageSize改变调用
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    getTableData(pageNo) {
      let self = this
      self.$axios.get('api/user/getUserList', {
        params: {
          pageSize: 10,
          currentPage: pageNo
        }
      }).then(res => {
        if (res.data.code == 200) {
          self.tableData = res.data.data.records;
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
        userName: [
          {required: true, message: "用户名不能为空", trigger: "blur"},
        ],
        userPassword: [
          {required: true, message: "密码不能为空", trigger: "blur"},
        ],
        userRoot: [
          {required: true, message: "权限等级不能为空", trigger: "blur"},
        ],
        userTell: [
          {required: true, message: "联系方式不能为空", trigger: "blur"},
        ],sex: [
          {required: true, message: "角色说明不能为空", trigger: "blur"},
        ],
        userNickname: [
          {required: true, message: "昵称不能为空", trigger: "blur"},
        ],
      },
      roleDialogVisible: false,
      //角色列表
      roleTableData: [],

      page: {
        currentPage: 1, // 当前页
        pageSize: 10, // 每页显示条目个数
        totalCount: 0 // 总条目数
      },

      //当前页树
      currentPage: 1,
      pageSize: 10,
      //表格高度
      tableHeight: 0,
      //搜索数据绑定
      searchForm: {
        userName: "",
        userTell: "",
      },
      addUserVisible: false,
      updateUserVisible: false,
      addForm: {sex: 1},
      updateForm: [],
      //表格数据
      tableData: [],
      userId: "",
      //当前选中的行
      currentRow: "",
      //要分配角色的用户id
      selectUserId: 0,
      //分配用户的角色id
      roleId: 0,
      first: true,
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
.roleClass /deep/ .el-table__body tr.current-row > td {
  background: #409eff !important;
  color: #fff;
}

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
  margin-bottom: 100px;
}

.self_dialog /deep/ .el-dialog .el-dialog__footer {
  left: 40%;
  bottom: 0;
  position: absolute;
}
</style>