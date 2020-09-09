<template>
  <el-main>
    <el-form size="mini" :model="selectValuablesFrom" label-width="80px">
      <el-row>
        <el-col :span="3">
          <el-form-item label="姓名:">
            <el-input v-model="selectValuablesFrom.valStuName" placeholder="请输入物品所有人姓名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item label="物品名:">
            <el-input v-model="selectValuablesFrom.valName" placeholder="请输入物品名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item label="备注:">
            <el-input v-model="selectValuablesFrom.valRemark" placeholder="请输入备注信息"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="selectByLike" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addOne" size="mini" type="primary" icon="el-icon-plus">新增物品</el-button>
        <el-button @click="addManyValuablesVisible = true" size="mini" type="primary" icon="el-icon-upload2">批量导入</el-button>
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
    <el-table
      :data="valuablesTable"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
      size="mini"
      :height="tableHeight"
      border
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="valStuName" label="姓名"></el-table-column>
      <el-table-column prop="valName" label="物品名"></el-table-column>
      <el-table-column prop="valNum" label="物品数量"></el-table-column>
      <el-table-column prop="valPrice" label="物品总价"></el-table-column>
      <el-table-column prop="valDate" label="登记日期" sortable></el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="details(scope.row)">详情</el-button>
          <el-button size="mini" type="success" @click="updateValuables(scope.row)">编辑</el-button>
          <el-popconfirm
            style="margin-left: 10px;"
            title="您确定删除吗？"
            icon="el-icon-info"
            iconColor="red"
            @onConfirm="deleteOne(scope.row.valId)"
          >
            <el-button size="mini" type="danger" slot="reference">删除</el-button>
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

    <el-dialog title="新增贵重物品信息" :visible.sync="Visible" width="25%">
      <el-form size="mini" ref="addOneData" :model="addOneData" label-width="85px">
        <el-form-item label="姓名">
          <el-input v-model="addOneData.valStuName" placeholder="请输入学生姓名"></el-input>
        </el-form-item>
        <el-form-item label="物品名">
          <el-input v-model="addOneData.valName" placeholder="请输入物品名"></el-input>
        </el-form-item>
        <el-row>
          <el-form-item label="物品数量">
            <el-input-number v-model="addOneData.valNum"></el-input-number>
          </el-form-item>
        </el-row>
        <el-form-item label="物品总价">
          <el-input type="number" v-model="addOneData.valPrice" placeholder="物品总价"></el-input>
        </el-form-item>
        <el-form-item label="物品规格">
          <el-input v-model="addOneData.valSpecs" placeholder="请输入物品规格"></el-input>
        </el-form-item>
        <el-form-item label="登记日期">
          <el-date-picker
            v-model="addOneData.valDate"
            type="date"
            placeholder="选择日期时间"
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="addOneData.valRemark" placeholder type="textarea"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="Visible = false">取 消</el-button>
        <el-button type="primary" @click="addOneSubmit">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="编辑贵重物品信息" :visible.sync="updateOneValuablesVisible" width="40%">
      <el-form
        ref="updateData"
        :model="updateData"
        size="mini"
        style="text-align:center"
        :inline="true"
        label-width="100px"
      >
        <el-input type="hidden" v-model="updateData.valId"></el-input>
        <el-form-item label="姓名">
          <el-input v-model="updateData.valStuName"></el-input>
        </el-form-item>
        <el-form-item label="物品名">
          <el-input v-model="updateData.valName"></el-input>
        </el-form-item>
        <el-form-item label="物品数量">
          <el-input v-model="updateData.valNum"></el-input>
        </el-form-item>
        <el-form-item label="物品总价">
          <el-input v-model="updateData.valPrice"></el-input>
        </el-form-item>
        <el-form-item label="物品规格">
          <el-input v-model="updateData.valSpecs"></el-input>
        </el-form-item>
        <el-form-item label="登记日期">
          <el-input v-model="updateData.valDate"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="updateData.valRemark"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneValuablesVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSubmit">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="贵重物品详情" :visible.sync="valuablesDetailsVisible" width="40%">
      <el-form
        :model="detailsData"
        size="mini"
        style="text-align:center"
        :inline="true"
        label-width="100px"
      >
        <el-form-item label="姓名">
          <el-input v-model="detailsData.valStuName" disabled></el-input>
        </el-form-item>
        <el-form-item label="物品名">
          <el-input v-model="detailsData.valName" disabled></el-input>
        </el-form-item>
        <el-form-item label="物品数量">
          <el-input v-model="detailsData.valNum" disabled></el-input>
        </el-form-item>
        <el-form-item label="物品总价">
          <el-input v-model="detailsData.valPrice" disabled></el-input>
        </el-form-item>
        <el-form-item label="物品规格">
          <el-input v-model="detailsData.valSpecs" disabled></el-input>
        </el-form-item>
        <el-form-item label="登记日期">
          <el-input v-model="detailsData.valDate" disabled></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="detailsData.valRemark" disabled></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="valuablesDetailsVisible = false">取 消</el-button>
      </span>
    </el-dialog>

    <el-dialog title="批量导入" :visible.sync="addManyValuablesVisible" width="30%" align="center">
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
        <el-button @click="addManyValuablesVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploads">确 定</el-button>
      </span>
    </el-dialog>

  </el-main>
</template>

<script>

  import qs from 'qs';

export default {
  methods: {
    addOne() {
      this.Visible = true;
      this.addOne.valNum = 1;
    },
    //模糊查询
    selectByLike() {
      this.$axios({
        method: "get",
        url: "api/valuables/selectByLike",
        params: {
          valStuName: this.selectValuablesFrom.valStuName,
          valName: this.selectValuablesFrom.valName,
          valRemark: this.selectValuablesFrom.valRemark,
        },
      }).then((result) => {
        this.valuablesTable = result.data;
      });
    },
    //确认添加
    addOneSubmit() {
      this.$refs.addOneData.validate((valid) => {
        if (valid) {
          this.$axios.post("api/valuables/insertOneVal", qs.stringify(this.addOneData)).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.Visible = false;
              this.$refs.addOneData.resetFields(); // 刷新表单
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
    //确认编辑
    updateSubmit() {
      this.$refs.updateData.validate((valid) => {
        if (valid) {
          this.$axios.post("api/valuables/updateOneVal", qs.stringify(this.updateData)).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.updateOneValuablesVisible = false;
              this.getTableData(this.page.currentPage);
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
    //详情
    details(row) {
      //console.log(row);
      this.valuablesDetailsVisible = true;
      this.detailsData = row;
    },
    //编辑
    updateValuables(row) {
      //console.log(row);
      this.updateOneValuablesVisible = true;
      this.updateData = row;
    },
    //单个删除
    deleteOne(valId) {
      this.$axios({
        method: "get",
        url: "api/valuables/deleteOne",
        params: {
          valId: valId,
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
        url: "api/valuables/uploads",
        data: data
      }).then((result) => {
        if (result.data.code == 200) {
          this.$message({
            type: "success",
            duration: 1000,
            message: result.data.msg,
          });
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
        url: "api/valuables/getBatchInputTemplate",
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
        url: "api/valuables/download",
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
    //批量删除
    deleteList() {
      this.$confirm("是否删除所选信息?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.tableChecked.forEach((row) => {
          this.ids.push(row.valId);
        });
        console.log(this.ids);
        this.$axios({
          method: "delete",
          url: "api/valuables/delSelected",
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
    //复选框事件
    handleSelectionChange(val) {
      // console.log(val);
      this.tableChecked = val;
    },
    //pageSize改变调用
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    getTableData(pageNo) {
      let self = this
      self.$axios.get('api/valuables/getValuablesList', {
        params: {
          pageSize: 10,
          currentPage: pageNo
        }
      }).then(res => {
        if (res.data.code == 200) {
          self.valuablesTable = res.data.data.records;
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
    }
  },

  data() {
    return {
      //表单验证
      rules: {
        valNum: [{ pattern: /^[1-9]\d*/ }],
      },

      page: {
        currentPage: 1, // 当前页
        pageSize: 10, // 每页显示条目个数
        totalCount: 0 // 总条目数
      },

      Visible: false,
      title: "",
      addOneData: { valNum: 1 },
      tableHeight: 0,
      valuablesDetailsVisible: false,
      updateOneValuablesVisible: false,
      addManyValuablesVisible: false,
      files: [],
      detailsData: [],
      updateData: [],
      tableChecked: [], //选中显示的值
      ids: [],
      selectValuablesFrom: {
        valStuName: "",
        valName: "",
        valRemark: "",
      },
      //表格数据
      valuablesTable: [],
      valuablesDetails: {},
    };
  },
  created: function () {
    this.getTableData(1);
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 270;
    });
  },
};
</script>

<style>
</style>