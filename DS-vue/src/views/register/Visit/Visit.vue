<template>
  <el-main>
    <el-form size="mini" :model="selectVisitsFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="学号:">
            <el-input v-model="selectVisitsFrom.visStuNo" placeholder="请输入访客学号"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="selectByLike(selectVisitsFrom.visStuNo)" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addOne" size="mini" type="primary" icon="el-icon-plus">单个新增</el-button>
        <el-button @click="writeOut" size="mini" type="primary" icon="el-icon-files">批量导出</el-button>
        <el-button @click="deleteList(tableChecked)"  size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>
      </el-row>
    </el-form>
    <el-table
      ref="VisitsTable"
      tooltip-effect="dark"
      style="width: 100%"
      sortable
      :data="VisitsTable.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
      @selection-change="handleSelectionChange"
      size="mini"
      :height="tableHeight"
      border
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="学号" width="120" prop="visStuNo"></el-table-column>
      <el-table-column prop="visName" label="姓名" width="120"></el-table-column>
      <el-table-column prop="visNum" label="来访人数"></el-table-column>
      <el-table-column prop="visBld" label="所访宿舍楼"></el-table-column>
      <el-table-column prop="visRoom" label="所访宿舍号"></el-table-column>
      <el-table-column prop="visReason" label="原因" show-overflow-tooltip></el-table-column>
      <el-table-column prop="visTime" label="来访时间" show-overflow-tooltip sortable></el-table-column>
      <el-table-column prop="visLeave" label="离开时间" show-overflow-tooltip sortable></el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="details(scope.row.visId)" >详情</el-button>
          <el-button size="mini" type="success" @click="findOne(scope.row.visId)" >编辑</el-button>
          <el-popconfirm
                  style="margin-left: 10px;"
                  title="您确定删除吗？"
                  icon="el-icon-info"
                  iconColor="red"
                  @onConfirm="deleteOne(scope.row.visId)"
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
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="VisitsTable.length"
    ></el-pagination>


    <!--    单个新增模态框-->
    <el-dialog :title="title" :visible.sync="addOneVisible" width="40%">
      <el-form size="mini" ref="addOneDate" :model="addOneDate" :inline="true" label-width="85px">
        <el-form-item label="学号">
          <el-input v-model="addOneDate.visStuNo" placeholder="请输入学生学号"></el-input>
        </el-form-item>
        <el-form-item label="所访宿舍楼">
          <el-input v-model="addOneDate.visBld" placeholder="请输入所访宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="所访宿舍号">
          <el-input v-model="addOneDate.visRoom" placeholder="请输入所访宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="来访原因">
          <el-input v-model="addOneDate.visReason" placeholder="请输入来访原因" type="textarea"></el-input>
        </el-form-item>
        <el-row>
          <el-form-item label="来访人数">
            <el-input-number v-model="addOneDate.visNum" placeholder="请输入来访人数"></el-input-number>
          </el-form-item>
        </el-row>

        <el-form-item label="来访时间">
          <el-date-picker v-model="addOneDate.visTime"  value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="离开时间">
          <el-date-picker v-model="addOneDate.visLeave" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="addOneDate.visRemark" placeholder type="textarea"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addOneVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOneSubmit">确 定</el-button>
      </span>
    </el-dialog>

    <!--    详情模态框-->
    <el-dialog :title="title" :visible.sync="detailVisible" width="40%">
      <el-form size="mini" ref="visDetail" :model="visDetail" :inline="true" label-width="85px">
        <el-form-item label="学号">
          <el-input v-model="visDetail.visStuNo" disabled></el-input>
        </el-form-item>
        <el-form-item label="所访宿舍楼">
          <el-input v-model="visDetail.visBld" disabled></el-input>
        </el-form-item>
        <el-form-item label="所访宿舍号">
          <el-input v-model="visDetail.visRoom" disabled></el-input>
        </el-form-item>
        <el-form-item label="来访原因">
          <el-input v-model="visDetail.visReason" disabled type="textarea"></el-input>
        </el-form-item>
        <el-row>
          <el-form-item label="来访人数">
            <el-input-number v-model="visDetail.visNum" disabled></el-input-number>
          </el-form-item>
        </el-row>

        <el-form-item label="来访时间">
          <el-date-picker v-model="visDetail.visTime" type="datetime" disabled></el-date-picker>
        </el-form-item>
        <el-form-item label="离开时间">
          <el-date-picker v-model="visDetail.visLeave" type="date" disabled></el-date-picker>
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="visDetail.visRemark"  type="textarea" disabled></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">取 消</el-button>
      </span>
    </el-dialog>

    <!--    修改模态框-->
    <el-dialog :title="title" :visible.sync="updateVisible" width="40%">
      <el-form size="mini" ref="updateData" :model="updateData" :inline="true" label-width="85px">
        <el-form-item label="学号">
          <el-input v-model="updateData.visStuNo" placeholder="请输入学生学号"></el-input>
        </el-form-item>
        <el-form-item label="所访宿舍楼">
          <el-input v-model="updateData.visBld" placeholder="请输入所访宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="所访宿舍号">
          <el-input v-model="updateData.visRoom" placeholder="请输入所访宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="来访原因">
          <el-input v-model="updateData.visReason" placeholder="请输入来访原因" type="textarea"></el-input>
        </el-form-item>
        <el-row>
          <el-form-item label="来访人数">
            <el-input-number v-model="updateData.visNum" placeholder="请输入来访人数"></el-input-number>
          </el-form-item>
        </el-row>

        <el-form-item label="来访时间">
          <el-date-picker v-model="updateData.visTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="离开时间">
          <el-date-picker v-model="updateData.visLeave" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="updateData.visRemark" placeholder type="textarea"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="updateVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSubmit">确 定</el-button>
      </span>
    </el-dialog>


  </el-main>
</template>

<script>
export default {
  methods: {
    addOne() {
      this.addOneVisible = true;
      this.title='添加访客记录';
    },
    getVisitList(){
      this.$axios({
        method: "get",
        url: "api/visits/visitList"
      }).then((result) =>{
        this.VisitsTable = result.data;
        //console.log(this.VisitsTable);
      })
    },
    selectByLike(visStuNo) {
      this.$axios({
        method: "get",
        url: "api/visits/selectByLike",
        params: {
          visStuNo: visStuNo,
        }
      }).then((result) =>{
        this.VisitsTable = result.data;
        //console.log(this.enterData);
      })
    },
    addOneSubmit(){
      this.$refs.addOneDate.validate((valid) => {
        if(valid){
          var data = new FormData();
          data.append('visStuNo',this.addOneDate.visStuNo);
          data.append('visBld',this.addOneDate.visBld);
          data.append('visRoom',this.addOneDate.visRoom);
          data.append('visReason',this.addOneDate.visReason);
          data.append('visNum',this.addOneDate.visNum);
          data.append('visTime',this.addOneDate.visTime);
          data.append('visLeave',this.addOneDate.visLeave);
          data.append('visRemark',this.addOneDate.visRemark);
          console.log(data);
          this.$axios({
            method: "post",
            url: "api/visits/insertOneStu",
            data: data
          }).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.addOneVisible = false;
              this.$refs.addOneDate.resetFields(); // 刷新表单
              this.getVisitList();
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
    deleteOne(visId){
      this.$axios({
        method: "get",
        url: "api/visits/deleteOne",
        params: {
          visId: visId,
        }
      }).then((result) => {
        if (result.data.code == 200) {
          this.$message({
            type: "success",
            duration: 1000,
            message: result.data.msg,
          });
          this.getVisitList();
        } else {
          this.$message({
            type: "error",
            duration: 1000,
            message: result.data.msg,
          });
        }
      });
    },
    deleteList(){
      //console.log(this.tableChecked);
      this.$confirm('是否删除所选信息?','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(()=>{
        this.tableChecked.forEach(id =>{
          this.ids.push(id.visId);
        });
        console.log(this.ids);
        this.$axios({
          method: "delete",
          url: "api/visits/delete",
          headers: {
            'Content-Type': 'application/json;charset=UTF-8'
          },
          data: JSON.stringify(this.ids),
        }).then((result) => {
          if (result.data.code == 200) {
            this.$message({
              type: "success",
              duration: 1000,
              message: result.data.msg,
            });
            this.getVisitList();
            this.ids = [];
          } else {
            this.$message({
              type: "error",
              duration: 1000,
              message: result.data.msg,
            });
          }
        });
      })
    },
    handleSelectionChange(val){
      // console.log(val);
      this.tableChecked = val;
    },
    details(visId){
      this.detailVisible = true;
      //console.log(visId)
      this.$axios({
        method: "get",
        url: "api/visits/findOne",
        params: {
          visId: visId,
        }
      }).then((result) =>{
        this.visDetail = result.data;
        //console.log(this.updateCheckin);
      })
    },
    findOne(visId){
      this.updateVisible = true;
      //console.log(enterId)
      this.$axios({
        method: "get",
        url: "api/visits/findOne",
        params: {
          visId: visId,
        }
      }).then((result) =>{
        this.updateData = result.data;
        //console.log(this.updateCheckin);
      })
    },
    updateSubmit(){
      this.$refs.updateData.validate((valid) => {
        if(valid){
          var data = new FormData();
          data.append('visId',this.updateData.visId)
          data.append('visStuNo',this.updateData.visStuNo)
          data.append('visBld',this.updateData.visBld);
          data.append('visRoom',this.updateData.visRoom);
          data.append('visReason',this.updateData.visReason);
          data.append('visNum',this.updateData.visNum);
          data.append('visTime',this.updateData.visTime);
          data.append('visLeave',this.updateData.visLeave);
          data.append('visRemark',this.updateData.visRemark);
          console.log(data);
          this.$axios({
            method: "post",
            url: "api/visits/updateOne",
            data: data
          }).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.updateVisible = false;
              this.getVisitList();
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
    writeOut(){
      this.$axios({
        method: 'post',
        url: "api/visits/writeOut",
        responseType: 'arraybuffer'
      }).then((res) => {
        let url = window.URL.createObjectURL(new Blob([res.data]));
        let a = document.createElement('a');
        a.setAttribute("download","Visit.xlsx");
        a.href = url;
        a.click();
      });

    },

    //pageSize改变调用
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 页数改变调用
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
    },
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
    },


  },
  data() {
    return {
      title:'',
      pageSize: 10,
      currentPage: 1,
      addOneDate: {},
      addOneVisible: false,
      detailVisible: false,
      updateVisible: false,
      tableHeight: 0,
      selectVisitsFrom: {
        visStuNo: "",
      },
      VisitsTable: [],
      tableChecked: [],//选中显示的值
      ids:[],//批量删除id
      visDetail: {},
      updateData: {},
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 270;
    });
  },
  created: function () {
    this.getVisitList();
  },
};
</script>

<style>
</style>