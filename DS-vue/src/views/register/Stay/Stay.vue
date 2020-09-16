<template>
  <el-main>
    <el-form size="mini" :model="selectFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="学号:">
            <el-input v-model="selectFrom.stayStuNo" placeholder="请输入学号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="宿舍楼:">
            <el-input v-model="selectFrom.stayBld" placeholder="请输入宿舍楼"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="select(selectFrom)" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addOne" size="mini" type="primary" icon="el-icon-plus">留宿申请</el-button>
        <el-button
                @click="deleteList(tableChecked)"
                size="mini" type="primary"
                icon="el-icon-delete"
        >批量撤销</el-button>
      </el-row>
    </el-form>
    <el-table
      :data="stayData.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
      ref="stayData"
      style="width: 100%"
      @selection-change="handleSelectionChange"
      size="mini"
      :height="tableHeight"
      border>
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="stayStuNo" label="学号"></el-table-column>
      <el-table-column prop="stayBld" label="宿舍楼"></el-table-column>
      <el-table-column prop="stayRoom" label="房间号"></el-table-column>
      <el-table-column prop="stayAudit" :formatter="auditFormat" label="审核状态"></el-table-column>
      <el-table-column label="操作" width="350" align="center">
        <template slot-scope="scope">
          <el-button
                  @click="auditStay(scope.row)"
                  type="primary"
                  size="mini"
                  :disabled="scope.row.stayAudit == 0 ? false: true"
          >审核</el-button>
          <el-button size="mini" type="primary" @click="details(scope.row)">详情</el-button>
          <el-button :disabled="scope.row.stayAudit == 0 ? false: true" size="mini" type="success" @click="updateStay(scope.row)">编辑</el-button>
          <el-popconfirm
            style="margin-left: 10px;"
            title="您确定要撤销这条申请记录吗？"
            icon="el-icon-info"
            iconColor="red"
            @onConfirm="deleteOne(scope.row.stayId)"
          >
            <el-button size="mini" type="danger" slot="reference" >撤销</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-size="pageSize"
      layout="total, prev, pager, next"
      :total="stayData.length"
    ></el-pagination>

<!--    留宿申请-->
    <el-dialog title="留宿申请表" :visible.sync="addVisible" width="35%">
      <el-form
              class="addOneData"
              ref="addOneData"
              size="mini"
              :model="addOneData"
              :inline="true"
              label-width="85px">
        <el-form-item label="学号" prop="stayStuNo">
          <el-input v-model="addOneData.stayStuNo" placeholder="请输入学生学号"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼" prop="stayBld">
          <el-input v-model="addOneData.stayBld" placeholder="请输入申请留宿的宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="房间号" prop="stayRoom">
<!--          <el-input v-model="addOneData.stayRoom" placeholder="请输入申请的留宿房间号"></el-input>-->
          <el-select v-model="addOneData.stayRoom" placeholder="请选择房间号">
            <el-option
                    v-for="item in room"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="留宿日期" prop="stayDate">
          <el-date-picker
                  v-model="addOneData.stayDate"
                  type="daterange"
                  :picker-options="pickerOptions"
                  @change="dateChange"
                  unlink-panels
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="留宿原因" prop="stayReason">
          <el-input v-model="addOneData.stayReason" placeholder="请输入留宿原因" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button @click="addOneSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

    <!--详情模态框-->
    <el-dialog title="退宿申请详情" :visible.sync="stayDetailsVisible" width="30%">
      <el-form
              class="stayDetails"
              ref="stayDetails"
              size="mini"
              style="text-align:center"
              :model="stayDetails"
              :inline="true"
              label-width="90px">
        <el-form-item label="学号">
          <el-input v-model="stayDetails.stayStuNo" disabled></el-input>
        </el-form-item>
        <el-form-item label="申请人姓名">
          <el-input  v-model="stayDetails.stuName" disabled></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼">
          <el-input  v-model="stayDetails.stayBld" disabled></el-input>
        </el-form-item>
        <el-form-item label="房间号">
          <el-input  v-model="stayDetails.stayRoom" disabled></el-input>
        </el-form-item>
        <el-form-item label="留宿日期">
          <el-input  v-model="stayDetails.stayDate" disabled></el-input>
        </el-form-item>
        <el-form-item label="留宿原因" >
          <el-input v-model="stayDetails.stayReason" type="textarea" disabled></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
         <el-button @click="stayDetailsVisible = false">返 回</el-button>
      </span>
    </el-dialog>

    <!--审核模态框-->
    <el-dialog title="审核" :visible.sync="auditVisible" width="30%">
      <el-form
              class="auditData"
              ref="auditData"
              size="mini"
              style="text-align:center"
              :model="auditData"
              :inline="true"
              label-width="100px">
        <el-form-item label="退宿原因">
          <el-input type="textarea" v-model="auditData.stayReason" disabled></el-input>
        </el-form-item>
        <el-form-item label="审核结果" >
          <el-radio-group v-model="auditData.stayAudit">
            <el-radio :label="1">审核通过</el-radio>
            <el-radio :label="2">审核未通过</el-radio>
          </el-radio-group>
        </el-form-item>
        <br/>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="auditVisible = false">取 消</el-button>
        <el-button @click="auditSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

    <!--修改模态框-->
    <el-dialog title="修改留宿申请" :visible.sync="updateVisible" width="40%">
      <el-form
              class="updateData"
              ref="updateData"
              size="mini"
              style="text-align:center"
              :model="updateData"
              :inline="true"
              label-width="100px">
        <el-form-item label="学号">
          <el-input v-model="updateData.stayStuNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼">
          <el-input v-model="updateData.stayBld" placeholder="请输入退宿宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="宿舍号">
<!--          <el-input v-model="updateData.stayRoom" placeholder="请输入退宿宿舍号"></el-input>-->
          <el-select v-model="updateData.stayRoom" placeholder="请选择房间号">
            <el-option
                    v-for="item in room"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="留宿日期">
          <el-date-picker
                  v-model="updateData.stayDate"
                  type="daterange"
                  :picker-options="pickerOptions"
                  @change="dateChange"
                  unlink-panels
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="留宿原因">
          <el-input v-model="updateData.stayReason" type="textarea" placeholder="请输入退宿原因"></el-input>
        </el-form-item>
        <br/>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateVisible = false">取 消</el-button>
        <el-button @click="updateSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>


  </el-main>

</template>

<script>
export default {
  created: function () {
    this.getStayData();
    this.roomNoList();
  },
  methods: {
    //模态框
    addOne() {
      this.addVisible = true;
    },
    details(row){
      this.stayDetails = row;
      this.$axios({
        method: "get",
        url: "api/students/selectByStuNo",
        params: {
          stuNo: row.stayStuNo,
        }
      }).then((result) =>{
        this.stayDetails.stuName = result.data.stuName;
        // console.log(this.stayDetails);
        this.stayDetailsVisible = true;
      })
    },
    auditStay(row){
      this.auditData.stayId = row.stayId;
      this.auditData.stayReason = row.stayReason;
      this.auditVisible = true;
    },
    updateStay(row){
      this.updateData = row;
      this.updateVisible = true;
    },

    //功能
    getStayData(){
      this.$axios({
        method: "get",
        url: "api/stay/stayList"
      }).then((result) =>{
        this.stayData = result.data;
        console.log(this.stayData);
      })
    },
    select(row){
      // alert(row.stayStuNo+row.stayBld+row.stayRoom);
      if(row.stayStuNo==""){
        row.stayStuNo = null;
      }
      if(row.stayBld==""){
        row.stayBld = null;
      }
      if(row.stayStuNo == null&&row.stayBld==null){
        this.getStayData();
      }
      this.$axios({
        method: "get",
        url: "api/stay/selectByFrom",
        params: {
          stayStuNo: row.stayStuNo,
          stayBld: row.stayBld,
        }
      }).then((result) =>{
        this.stayData = result.data;
        console.log(this.stayData);
      })
    },
    roomNoList(){
      this.$axios({
        method: "get",
        url: "api/room/roomList"
      }).then((result) => {
        let roomList = result.data;
        for (let i=0;i<roomList.length;i++){
          let roomNo={
            value:roomList[i].roomNo,
            label:roomList[i].roomBld+roomList[i].roomNo,
          };
          this.room.push(roomNo);
        }
      })
    },

    addOneSubmit(){
      this.$refs.addOneData.validate((valid) =>{
       if(valid){
         var data = new FormData();
         data.append('stayStuNo',this.addOneData.stayStuNo);
         data.append('stayBld',this.addOneData.stayBld);
         data.append('stayRoom',this.addOneData.stayRoom);
         data.append('stayDate',this.addOneData.stayDate[0]+"至"+this.addOneData.stayDate[1]);
         data.append('stayReason',this.addOneData.stayReason);
         this.$axios({
           method: "post",
           url: "api/stay/insertOneStay",
           data: data,
         }).then(()=>{
           this.addVisible = false;
           this.$refs.addOneData.resetFields();
           this.getStayData();
         })
       }
      });
    },

    auditSubmit(){
      this.$refs.auditData.validate((valid)=>{
        if(valid){
          var data = new FormData();
          data.append('stayId',this.auditData.stayId);
          data.append('stayAudit',this.auditData.stayAudit);
          this.$axios({
            method: "post",
            url: "api/stay/updateStay",
            data: data,
          }).then(()=>{
            alert("审核操作成功！")
          })
        }
      })
      this.auditVisible = false;
      this.getStayData();
    },
    updateSubmit(){
      this.$refs.updateData.validate((valid)=>{
        if(valid){
          var data = new FormData();
          data.append('stayId',this.updateData.stayId);
          data.append('stayStuNo',this.updateData.stayStuNo);
          data.append('stayBld',this.updateData.stayBld);
          data.append('stayRoom',this.updateData.stayRoom);
          data.append('stayReason',this.updateData.stayReason);
          data.append('stayDate',this.updateData.stayDate[0]+"至"+this.updateData.stayDate[1]);
          this.$axios({
            method: "post",
            url: "api/stay/updateStay",
            data: data,
          }).then(()=>{
            alert("修改操作成功！")
          })
        }
      })
      this.auditVisible = false;
      this.getStayData();
    },

    deleteOne(id){
      this.$axios({
        method: "delete",
        url: "api/stay/deleteOne",
        params: {
          stayId: id,
        }
      }).then(()=>{
        alert("已撤销本条记录！");
        location.reload();
      })
    },
    deleteList(){
      if(this.tableChecked.length ===0){
        alert("选择为空");
      }else {
        this.$confirm('是否删除所选申请记录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          this.tableChecked.forEach(id => {
            this.ids.push(id.stayId);
          });
          this.$axios({
            method: "delete",
            url: "api/stay/delete",
            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            },
            data: JSON.stringify(this.ids),
          }).then(() => {
            alert("批量撤销成功！");
            location.reload();
          })
        })
      }
    },

    //获取选项框的值
    handleSelectionChange(val){
      // console.log(val);
      this.tableChecked = val;
    },
    auditFormat(row){
      if(row.stayAudit == 1){
        return "审核通过";
      }else if(row.stayAudit == 2){
        return  "审核未通过";
      }else{
        return  "审核中";
      }
    },
    dateChange(val){
      console.log(val);
      this.addOneData.stayDate = val;
    },
    //pageSize改变调用
    handleSizeChange(val) {
      // alert(`每页 ${val} 条`) ;
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
      pageSize:5,
      currentPage:1,
      mode: {},
      addVisible: false,
      stayDetailsVisible: false,
      auditVisible: false,
      updateVisible: false,
      addOneData: [],
      updateData: [],
      tableHeight: window.innerHeight,
      selectFrom: [],
      stayDetails:[],
      stayData:[],
      auditData:[],
      tableChecked:[],
      room:[],
      ids:[],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 285;
    });
  },
};
</script>

<style>
</style>