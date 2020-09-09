<template>
  <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="moveFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="名称:">
            <el-input v-model="moveFrom.moveStuNo" placeholder="请输入调宿学生学号"></el-input>
          </el-form-item>
        </el-col>
        <el-button
          @click="selectByLike(moveFrom.moveStuNo)"
          style="margin-left: 20px;"
          class="btn-left"
          size="mini"
          type="primary"
          icon="el-icon-search"
        >查询</el-button>
        <el-button
          @click="addOneMoveVisible = true"
          size="mini"
          type="primary"
          icon="el-icon-plus"
        >新增调宿</el-button>
        <el-button @click="addManyMove" size="mini" type="primary" icon="el-icon-upload2">批量导入</el-button>
        <el-button @click="getBatchInputTemplate" size="mini" type="primary" icon="el-icon-download">批量导入模板下载</el-button>
        <el-button @click="download" size="mini" type="primary" icon="el-icon-download">批量导出</el-button>
        <el-button
          @click="batchAudit"
          size="mini"
          type="primary"
          icon="el-icon-edit-outline"
          :disabled="this.batchAuditIds.length === 0"
        >批量审核</el-button>
        <el-button
          @click="deleteList"
          size="mini"
          type="primary"
          icon="el-icon-delete"
          :disabled="this.tableChecked.length === 0"
        >批量撤销</el-button>
      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table
      :data="moveData"
      @selection-change="handleSelectionChange"
      :height="tableHeight"
      size="mini"
      border
      style="width: 100%"
    >
      <el-table-column size="mini" align="center" type="selection"></el-table-column>
      <el-table-column align="center" prop="moveStuNo" label="学号"></el-table-column>
      <el-table-column align="center" prop="moveStuName" label="姓名"></el-table-column>
      <el-table-column align="center" prop="moveOldBld" label="原宿舍楼"></el-table-column>
      <el-table-column align="center" prop="moveOldRoom" label="原宿舍号"></el-table-column>
      <el-table-column align="center" prop="moveNewBld" label="申请宿舍楼"></el-table-column>
      <el-table-column align="center" prop="moveNewRoom" label="申请宿舍号"></el-table-column>
      <el-table-column align="center" prop="moveReason" label="调宿原因"></el-table-column>
      <el-table-column align="center" :formatter="stateFormat" prop="moveAudit" label="审核情况"></el-table-column>

      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            @click="pass(scope.row)"
            type="primary"
            :disabled="scope.row.moveAudit == 0 ? false : true"
            size="mini"
          >通过审核</el-button>
          <el-button @click="details(scope.row.moveId)" type="primary" size="mini">详情</el-button>
          <el-button @click="updateMove(scope.row)" type="success" size="mini">编辑</el-button>
          <el-popconfirm
            style="margin-left: 10px;"
            title="您确定撤销吗？"
            icon="el-icon-info"
            iconColor="red"
            @onConfirm="deleteOne(scope.row.moveId)"
          >
            <el-button size="mini" type="danger" slot="reference">撤销</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件
      size-change: 当page-sizes 改变时触发事件  
      current-change：当页数发生变化时触发事件，  
      current-page：当前是第几页  
      page-size：页容量，也就是每页多少条数据  
      total:总共有多少条数据,后台返回数据数值
    -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page.currentPage"
      :page-size="page.pageSize"
      :total="page.totalCount"
      layout="total, prev, pager, next"
    ></el-pagination>

    <el-dialog title="新增调宿表" :visible.sync="addOneMoveVisible" width="40%">
      <el-form
        size="mini"
        ref="addOne"
        style="text-align:center"
        :model="addOne"
        :inline="true"
        label-width="100px"
        status-icon
        :rules="rules"
      >
        <el-form-item label="学号" prop="moveStuNo">
          <el-input v-model="addOne.moveStuNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="moveStuName">
          <el-input v-model="addOne.moveStuName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="原宿舍楼" prop="moveOldBld">
          <el-input v-model="addOne.moveOldBld" placeholder="请输入原宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="原宿舍号" prop="moveOldRoom">
          <el-input v-model="addOne.moveOldRoom" placeholder="请输入原宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍楼" prop="moveNewBld">
          <el-input v-model="addOne.moveNewBld" placeholder="请输入申请宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍号" prop="moveNewRoom">
          <el-input v-model="addOne.moveNewRoom" placeholder="请输入申请宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="申请原因" prop="moveReason">
          <el-input v-model="addOne.moveReason" placeholder="请输入申请原因"></el-input>
        </el-form-item>
        <br />
        <el-form-item label="审核情况">
          <el-radio-group v-model="addOne.moveAudit">
            <el-radio :label="0">未审核</el-radio>
            <el-radio :label="1">通过审核</el-radio>
            <el-radio :label="2">未通过审核</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOneMoveVisible = false">取 消</el-button>
        <el-button @click="addOneSubmit()" type="primary">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改调宿表" :visible.sync="updateOneMoveVisible" width="40%">
      <el-form
        size="mini"
        style="text-align:center"
        ref="updateData"
        :model="updateData"
        :inline="true"
        label-width="100px"
      >
        <el-form-item label="学号">
          <el-input v-model="updateData.moveStuNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="updateData.moveStuName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="原宿舍楼">
          <el-input v-model="updateData.moveOldBld" placeholder="请输入原宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="原宿舍号">
          <el-input v-model="updateData.moveOldRoom" placeholder="请输入原宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍楼">
          <el-input v-model="updateData.moveNewBld" placeholder="请输入申请宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍号">
          <el-input v-model="updateData.moveNewRoom" placeholder="请输入申请宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="申请原因">
          <el-input v-model="updateData.moveReason" placeholder="请输入申请原因"></el-input>
        </el-form-item>
        <br />
        <el-form-item label="审核情况">
          <el-radio-group v-model="updateData.moveAudit">
            <el-radio :label="0">未审核</el-radio>
            <el-radio :label="1">通过审核</el-radio>
            <el-radio :label="2">未通过审核</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneMoveVisible = false">取 消</el-button>
        <el-button @click="updateSubmit()" type="primary">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="批量新增" :visible.sync="addManyMoveVisible" width="30%" align="center">
      <el-upload
        action="#"
        ref="uploads"
        class="upload-demo"
        drag
        multiple
        accept=".xls, .xlsx"
        :auto-upload="false"
        :on-change="addFile"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件，且不超过500kb</div>
      </el-upload>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addManyMoveVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploads">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="退宿详情" :visible.sync="moveDetailsVisible" width="40%">
      <el-form
        :model="moveDetails"
        size="mini"
        style="text-align:center"
        :inline="true"
        label-width="100px"
      >
        <el-form-item label="学号">
          <el-input v-model="moveDetails.moveStuNo" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="moveDetails.moveStuName" disabled></el-input>
        </el-form-item>
        <el-form-item label="原宿舍楼">
          <el-input v-model="moveDetails.moveOldBld" disabled></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍楼">
          <el-input v-model="moveDetails.moveNewBld" disabled></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍号">
          <el-input v-model="moveDetails.moveNewRoom" disabled></el-input>
        </el-form-item>
        <el-form-item label="申请调宿原因">
          <el-input v-model="moveDetails.moveReason" disabled></el-input>
        </el-form-item>
        <br />
        <el-form-item label="审核情况">
          <el-radio-group v-model="moveDetails.moveAudit" disabled>
            <el-radio :label="0">未审核</el-radio>
            <el-radio :label="1">通过审核</el-radio>
            <el-radio :label="2">未通过审核</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="moveDetailsVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </el-main>
</template>

<script>

  import qs from 'qs';

export default {
  methods: {
    addManyMove() {
      this.addManyMoveVisible = true;
      this.addOne.moveAudit = 0;
    },
    updateMove(row) {
      //console.log(row);
      this.updateOneMoveVisible = true;
      this.updateData = row;
    },
    stateFormat(row) {
      if (row.moveAudit == 0) {
        return "未审核";
      } else if (row.moveAudit == 1) {
        return "通过审核";
      } else {
        return "未通过审核";
      }
    },

    //模糊查询
    selectByLike(moveStuNo) {
      this.$axios({
        method: "get",
        url: "api/move/selectByLike",
        params: {
          moveStuNo: moveStuNo,
        },
      }).then((result) => {
        this.moveData = result.data;
      });
    },
    //确认添加
    addOneSubmit() {
      this.$refs.addOne.validate((valid) => {
        if (valid) {
          var data = new FormData();
          /*data.append("moveStuNo", this.addOne.moveStuNo);
          data.append("moveStuName", this.addOne.moveStuName);
          data.append("moveOldBld", this.addOne.moveOldBld);
          data.append("moveOldRoom", this.addOne.moveOldRoom);
          data.append("moveNewBld", this.addOne.moveNewBld);
          data.append("moveNewRoom", this.addOne.moveNewRoom);
          data.append("moveReason", this.addOne.moveReason);
          data.append("moveAudit", this.addOne.moveAudit);*/
          // console.log(data);
          this.$axios.post("api/move/insertOneStu",qs.stringify(this.addOne)).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.addOneMoveVisible = false;
              this.$refs.addOne.resetFields(); // 刷新表单
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
        } else {
          return false;
        }
      });
    },
    //点击通过审核
    pass(row) {
      let _row = row;
      this.passForm = Object.assign({}, _row);
      //console.log(this.passForm.moveAudit)
      if (this.passForm.moveAudit == 0) {
        this.passForm.moveAudit = 1;
        var data = new FormData();
        data.append("moveId", this.passForm.moveId);
        data.append("moveStuNo", this.passForm.moveStuNo);
        data.append("moveStuName", this.passForm.moveStuName);
        data.append("moveOldBld", this.passForm.moveOldBld);
        data.append("moveOldRoom", this.passForm.moveOldRoom);
        data.append("moveNewBld", this.passForm.moveNewBld);
        data.append("moveNewRoom", this.passForm.moveNewRoom);
        data.append("moveReason", this.passForm.moveReason);
        data.append("moveAudit", this.passForm.moveAudit);
        this.$axios({
          method: "post",
          url: "api/move/updateOneStu",
          data: data,
        }).then((result) => {
          if (result.data.code == 200) {
            this.$message({
              type: "success",
              duration: 1000,
              message: result.data.msg,
            });
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
    },
    //点击详情
    details(moveId) {
      this.moveDetailsVisible = true;
      console.log(moveId);
      this.$axios({
        method: "get",
        url: "api/move/findOne",
        params: {
          moveId: moveId,
        },
      }).then((result) => {
        this.moveDetails = result.data;
        console.log(this.updateCheckin);
      });
    },
    //点击编辑
    updateSubmit() {
      this.$refs.updateData.validate((valid) => {
        if (valid) {
          var data = new FormData();
          this.$axios.post("api/move/updateOneStu",qs.stringify(this.updateData)).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.updateOneMoveVisible = false;
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
      });
    },
    //单个撤销
    deleteOne(moveId) {
      this.$axios({
        method: "get",
        url: "api/move/deleteOne",
        params: {
          moveId: moveId,
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
    addFile: function (file, fileList) {
      console.log("addFile");
      this.files = fileList;
    },
    //点击上传
    uploads() {
      if (this.files.length == 0) {
        this.$message({
          type: "warning",
          duration: 1000,
          message: "上传文件为空",
        });
        return;
      }
      this.$refs.uploads.submit();
      const data = new FormData();
      this.files.forEach((file) => {
        data.append("files", file.raw); //此处一定是append file.raw 上传文件只需维护fileList file.raw.name要加上
      });
      this.$axios({
        type: "multipart/form-data",
        method: "post",
        url: "api/move/uploads",
        data: data,
      }).then((result) => {
        if (result.data.code == 200) {
          this.$message({
            type: "success",
            duration: 1000,
            message: result.data.msg,
          });
          this.addManyMoveVisible = false;
          this.getTableData(this.page.currentPage);
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
    },
    getBatchInputTemplate(){
      this.$axios({
        url: "api/move/getBatchInputTemplate",
        method: "post",
      }).then((result) => {
        if (result.data.code == 200) {
          // console.log("download: " + result.data.data);
          let url = result.data.data;
          let a = document.createElement("a");
          a.href = url;
          a.click();
        } else {
          this.$message({
            type: "error",
            duration: 1000,
            message: "下载失败",
          });
        }
      });
    },
    //点击下载
    download() {
      this.$axios({
        url: "api/move/download",
        method: "post",
      }).then((result) => {
        if (result.data.code == 200) {
          // console.log("download: " + result.data.data);
          let url = result.data.data;
          let a = document.createElement("a");
          a.href = url;
          a.click();
        } else {
          this.$message({
            type: "error",
            duration: 1000,
            message: "下载失败",
          });
        }
      });
    },
    //批量撤销
    deleteList() {
      this.$confirm("是否撤销所选信息?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.tableChecked.forEach((row) => {
          this.ids.push(row.moveId);
        });
        console.log(this.ids);
        this.$axios({
          method: "delete",
          url: "api/move/delSelected",
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
          },
          data: JSON.stringify(this.ids),
        }).then((result) => {
          // console.log('result: '+result.data.code);
          if (result.data.code == 200) {
            this.$message({
              type: "success",
              duration: 1000,
              message: result.data.msg,
            });
            console.log('(this.page.totalCount-this.ids.length)%this.page.pageSize==0?'+((this.page.totalCount-this.ids.length)%this.page.pageSize==0));
            console.log('this.page.currentPage!=1?'+this.page.currentPage!=1)
            if (((this.page.totalCount-this.ids.length)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
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
      });
    },
    //批量审批
    batchAudit() {
      this.$axios({
        method: "post",
        url: "api/move/batchAudit",
        type: "application/json;charset=UTF-8",
        data: this.batchAuditIds,
      }).then((result) => {
        // console.log('result: '+result.data.code);
        if (result.data.code == 200) {
          this.$message({
            type: "success",
            duration: 1000,
            message: result.data.msg,
          });
          this.getTableData(this.page.currentPage);
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
      // console.log(val);
      this.batchAuditIds = [];
      val.forEach((row) => {
        if (row.moveAudit == "0") {
          this.batchAuditIds.push(row.moveId);
        }
      });
      this.tableChecked = val;
    },
    //pageSize改变调用
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    getTableData(pageNo) {
      let self = this
      self.$axios.get('api/move/getMoveList', {
        params: {
          pageSize: 10,
          currentPage: pageNo
        }
      }).then(res => {
        if (res.data.code == 200) {
          self.moveData = res.data.data.records;
          self.$set(self.page, 'totalCount', res.data.data.total)
          self.$set(self.page, 'pageSize', res.data.data.size)
          self.$set(self.page, 'currentPage', res.data.data.current)
        }
      });
    },
    // 页数改变调用
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.getTableData(val)
    },
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
    },
  },
  data() {
    return {
      //表单验证
      rules: {
        moveStuNo: [
          { required: true, message: "学号不能为空", trigger: "blur" },
        ],
        moveStuName: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
        ],
        moveOldBld: [
          { required: true, message: "原宿舍楼不能为空", trigger: "blur" },
        ],
        moveOldRoom: [
          { required: true, message: "原宿舍号不能为空", trigger: "blur" },
        ],
        moveNewBld: [
          { required: true, message: "学号不能为空", trigger: "blur" },
        ],
        moveNewRoom: [
          { required: true, message: "申请宿舍号不能为空", trigger: "blur" },
        ],
        moveReason: [
          { required: true, message: "申请原因不能为空", trigger: "blur" },
        ],
      },

      page: {
        currentPage: 1, // 当前页
        pageSize: 10, // 每页显示条目个数
        totalCount: 0 // 总条目数
      },

      files: [],
      addOneMoveVisible: false,
      addManyMoveVisible: false,
      updateOneMoveVisible: false,
      moveDetailsVisible: false,
      moveDetails: {},
      //搜索框数据绑定
      moveFrom: {
        moveStuNo: "",
      },
      addOne: { moveAudit: 0 },
      tableChecked: [], //选中显示的值
      ids: [],
      batchAuditIds: [],
      // 表格高度
      tableHeight: window.innerHeight,
      // 表格数据
      moveData: [],
      updateData: [],
    };
  },
  created: function () {
    this.getTableData(1);
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 280;
    });
  },
};
</script>

<style>
</style>