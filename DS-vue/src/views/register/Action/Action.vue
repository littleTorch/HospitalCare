<template>
  <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="actForm" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="名称:">
            <el-input v-model="actForm.actRoom" placeholder="请输入宿舍号"></el-input>
          </el-form-item>
        </el-col>
        <el-button
          @click="selectByLike(actForm.actRoom)"
          style="margin-left: 20px;"
          class="btn-left"
          size="mini"
          type="primary"
          icon="el-icon-search"
        >查询</el-button>
        <el-button @click="addOneAct" size="mini" type="primary" icon="el-icon-plus">新增操行</el-button>
        <el-button @click="addManyActionVisible = true" size="mini" type="primary" icon="el-icon-upload2">批量导入</el-button>
        <el-button @click="getBatchInputTemplate" size="mini" type="primary" icon="el-icon-download">批量导入模板下载</el-button>
        <el-button @click="download" size="mini" type="primary" icon="el-icon-download">批量导出</el-button>
        <el-button
          @click="deleteList"
          size="mini"
          type="primary"
          icon="el-icon-delete"
          :disabled="this.tableChecked.length === 0"
        >批量删除</el-button>
      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table
      :data="actData"
      @selection-change="handleSelectionChange"
      size="mini"
      :height="tableHeight"
      border
      style="width: 100%"
    >
      <el-table-column align="center" type="selection"></el-table-column>
      <el-table-column align="center" prop="actBld" label="宿舍楼"></el-table-column>
      <el-table-column align="center" prop="actRoom" label="宿舍号"></el-table-column>
      <el-table-column align="center" prop="actDetails" label="操行详情" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="actEvaluate" label="评价"></el-table-column>
      <el-table-column align="center" prop="actDate" label="日期"></el-table-column>

      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button @click="detailsAct(scope.row)" type="primary" size="mini">详情</el-button>
          <el-button @click="updateAct(scope.row)" type="success" size="mini">编辑</el-button>
          <el-popconfirm
            style="margin-left: 10px;"
            title="您确定删除吗？"
            icon="el-icon-info"
            iconColor="red"
            @onConfirm="deleteOne(scope.row.actId)"
          >
            <el-button type="danger" size="mini" slot="reference">删除</el-button>
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

    <el-dialog title="新增操行记录" :visible.sync="addOneActVisible" width="40%">
      <el-form
        ref="addOne"
        size="mini"
        style="text-align:center"
        :model="addOne"
        :inline="true"
        label-width="100px"
        :rules="rules"
      >
        <el-form-item label="宿舍楼" prop="actBld">
          <el-input v-model="addOne.actBld" placeholder="请输入宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="宿舍号" prop="actRoom">
          <el-input v-model="addOne.actRoom" placeholder="请输入宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="操行详情" prop="actDetails">
          <el-input v-model="addOne.actDetails" placeholder="请输入操行详情"></el-input>
        </el-form-item>
        <el-form-item label="评价" prop="actEvaluate">
          <el-input v-model="addOne.actEvaluate" placeholder="请输入评价"></el-input>
        </el-form-item>
        <el-form-item label="登记日期" prop="actDate">
          <el-date-picker
                  v-model="addOne.actDate"
                  type="date"
                  placeholder="选择日期时间"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOneActVisible = false">取 消</el-button>
        <el-button @click="addSubmit" type="primary">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改操行记录" :visible.sync="updateOneActVisible" width="40%">
      <el-form
        size="mini"
        style="text-align:center"
        :rules="rules"
        ref="updateOneFormRef"
        :model="updateOneForm"
        :inline="true"
        label-width="100px"
      >
        <el-form-item label="编号">
          <el-input v-model="updateOneForm.actId" placeholder="请输入编号" disabled></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼" prop="actBld">
          <el-input v-model="updateOneForm.actBld" placeholder="请输入宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="宿舍号" prop="actRoom">
          <el-input v-model="updateOneForm.actRoom" placeholder="请输入宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="操行详情" prop="actDetails">
          <el-input v-model="updateOneForm.actDetails" placeholder="请输入操行详情"></el-input>
        </el-form-item>
        <el-form-item label="评价" prop="actEvaluate">
          <el-input v-model="updateOneForm.actEvaluate" placeholder="评价"></el-input>
        </el-form-item>
        <el-form-item label="登记日期" prop="actDate">
          <el-date-picker
                  v-model="updateOneForm.actDate"
                  type="date"
                  placeholder="选择日期时间"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneActVisible = false">取 消</el-button>
        <el-button @click="updateSubmit()" type="primary">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="操行记录详情" :visible.sync="actDetailsVisible" width="40%">
      <el-form size="mini" style="text-align:center" :inline="true" label-width="100px" disabled>
        <el-form-item label="编号">
          <el-input v-model="detailsForm.actId" placeholder="请输入编号"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼">
          <el-input v-model="detailsForm.actBld" placeholder="请输入宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="宿舍号">
          <el-input v-model="detailsForm.actRoom" placeholder="请输入宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="操行详情">
          <el-input v-model="detailsForm.actDetails" placeholder="请输入操行详情"></el-input>
        </el-form-item>
        <el-form-item label="评价">
          <el-input v-model="detailsForm.actEvaluate" placeholder="评价"></el-input>
        </el-form-item>
        <el-form-item label="登记日期">
          <el-date-picker
                  v-model="detailsForm.actDate"
                  type="date"
                  placeholder="选择日期时间"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="actDetailsVisible = false">取 消</el-button>
      </span>
    </el-dialog>

    <el-dialog title="批量导入" :visible.sync="addManyActionVisible" width="30%" align="center">
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
        <el-button @click="addManyActionVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploads">确 定</el-button>
      </span>
    </el-dialog>
  </el-main>
</template>

<script>

  import qs from 'qs';

export default {
  methods: {
    addOneAct() {
      this.addOneActVisible = true;
    },
    detailsAct(row) {
      this.actDetailsVisible = true;
      this.detailsForm = row;
    },
    updateAct(row) {
      this.updateOneActVisible = true;
      this.updateOneForm = row;
    },
    //模糊查询
    selectByLike(actRoom) {
      this.$axios({
        method: "get",
        url: "api/action/selectByLike",
        params: {
          actRoom: actRoom,
        },
      }).then((result) => {
        this.actData = result.data;
      });
    },
    // 添加操作记录
    addSubmit(){
      this.$refs.addOne.validate(valid => {
        if (valid){
          this.$axios.post("api/action/insertOneVal",qs.stringify(this.addOne)).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.addOneActVisible = false;
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
        }
      })
    },
    //修改操行记录
    updateSubmit() {
      this.$refs.updateOneFormRef.validate((valid) => {
        if (valid) {
          this.$axios.post("api/action/updateOneVal",qs.stringify(this.updateOneForm)).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.updateOneActVisible = false;
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
    //单个删除
    deleteOne(actId) {
      this.$axios({
        method: "delete",
        url: "api/action/deleteOne",
        params: {
          actId: actId,
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
    //批量删除
    deleteList() {
      this.$confirm("是否删除所选信息?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.tableChecked.forEach((row) => {
          this.ids.push(row.actId);
        });
        console.log(this.ids);
        this.$axios({
          method: "delete",
          url: "api/action/delSelected",
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
          },
          data: JSON.stringify(this.ids),
        }).then((result) => {
          if (result.data.code == 200) {
            this.$message({
              type: "success",
              duration: 1000,
              message: result.data.msg,
            });
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
    addFile: function(file, fileList) {
      console.log('addFile');
      this.files=fileList;
    },
    //点击上传
    uploads() {
      if(this.files.length == 0){
        this.$message({
          type: 'warning',
          duration: 1000,
          message: '上传文件为空'
        })
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
        url: "api/action/uploads",
        data: data
      }).then((result) => {
        if (result.data.code == 200) {
          this.$message({
            type: "success",
            duration: 1000,
            message: result.data.msg,
          });
          this.addManyActionVisible = false;
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
    getBatchInputTemplate(){
      this.$axios({
        url: "api/action/getBatchInputTemplate",
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
        url: "api/action/download",
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
    //pageSize改变调用
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.getTableData(val);
    },
    getTableData(pageNo) {
      let self = this
      self.$axios.get('api/action/getActionList2', {
        params: {
          pageSize: 10,
          currentPage: pageNo
        }
      }).then(res => {
        if (res.data.code == 200) {
          self.actData = res.data.data.records;
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
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
    },
  },
  created() {
    this.getTableData(1);
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 280;
    });
  },
  data() {
    return {
      //表单验证
      rules: {
        actBld: [
          { required: true, message: "宿舍楼不能为空", trigger: "blur" },
        ],
        actRoom: [
          { required: true, message: "宿舍号不能为空", trigger: "blur" },
        ],
        actDetails: [
          { required: true, message: "操行详情不能为空", trigger: "blur" },
        ],
        actEvaluate: [
          { required: true, message: "评价不能为空", trigger: "blur" },
        ],
        actDate: [
          { required: true, message: "登记日期不能为空", trigger: "blur" },
        ],
      },

      page: {
        currentPage: 1, // 当前页
        pageSize: 10, // 每页显示条目个数
        totalCount: 0 // 总条目数
      },

      addOneActVisible: false,
      updateOneActVisible: false,
      actDetailsVisible: false,
      addOne: [],
      addManyActionVisible: false,
      files: [],
      //搜索框数据绑定
      actForm: {
        actRoom: "",
      },
      detailsForm: [],
      updateOneForm: [],
      tableChecked: [], //选中显示的值
      ids: [],
      // 表格高度
      tableHeight: '',
      // 表格数据
      actData: [],
    };
  },
};
</script>

<style>
</style>