<template>
  <el-main>
    <el-form size="mini" :model="selectHistoryFrom" label-width="80px">
      <el-row>
        <el-col :span="4">
          <el-form-item label="姓名:">
            <el-input v-model="selectHistoryFrom.hisStuName" placeholder="请输入入住人姓名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="学号:">
            <el-input v-model="selectHistoryFrom.hisStuNo" placeholder="请输入学号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="宿舍楼:">
            <el-input v-model="selectHistoryFrom.hisBld" placeholder="请输入证件号码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="宿舍号:">
            <el-input v-model="selectHistoryFrom.hisRoom" placeholder="请输入宿舍号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
        <el-form-item label="退宿原因:">
          <el-input v-model="selectHistoryFrom.hisReason" placeholder="请输入证件号码"></el-input>
        </el-form-item>
        </el-col>
        <el-button @click="sel(1)" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="del" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>
      </el-row>
    </el-form>
    <el-table
      ref="historyTable"
      :data="historyTable"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
      size="mini"
      :height="tableHeight"
      border
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="学号" width="120" prop="hisStuNo"></el-table-column>
      <el-table-column prop="hisStuName" label="姓名" width="120"></el-table-column>
      <el-table-column prop="hisBld" label="宿舍楼"></el-table-column>
      <el-table-column prop="hisRoom" label="宿舍号"></el-table-column>
      <el-table-column prop="hisReason" label="原因" show-overflow-tooltip></el-table-column>
      <el-table-column :formatter="StateFormat" prop="hisState" label="住宿状态"></el-table-column>
      <el-table-column :formatter="IsdeleteFormat" prop="hisIsdelete" label="撤销操作"></el-table-column>
      <el-table-column prop="hisDate" label="记录时间" sortable></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button @click="selectRoom(scope.row)" size="mini" type="primary">宿舍详情</el-button>
          <el-popconfirm @onConfirm="handleEdit(scope.row)" style="margin-left: 10px;" title="您确定删除吗？" icon="el-icon-info" iconColor="red">
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

    <el-dialog title="宿舍详情" :visible.sync="roomVisible" width="40%">
      <el-form
              size="mini"
              style="text-align:center"
              :model="room"
              :inline="true"
              label-width="100px"
              disabled
      >
        <el-form-item label="宿舍号">
          <el-input v-model="room.roomNo" placeholder="请输入宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼">
          <el-input v-model="room.roomBld" placeholder="请输入居住最大人数"></el-input>
        </el-form-item>
        <el-form-item label="宿舍类型">
          <el-input v-model="room.roomAddress" placeholder="请输入居住最大人数"></el-input>
        </el-form-item>
        <el-form-item label="宿舍费用">
          <el-input v-model="room.roomCost" placeholder="宿舍费用"></el-input>
        </el-form-item>
        <br/>
        <el-form-item label="房间类型">
          <el-radio-group v-model="room.roomType">
            <el-radio :label="0">4人间</el-radio>
            <el-radio :label="1">6人间</el-radio>
            <el-radio :label="2">8人间</el-radio>
          </el-radio-group>
        </el-form-item>
        <br/>
        <el-form-item label="是否满员">
          <el-radio-group v-model="room.roomState">
            <el-radio :label="0">空</el-radio>
            <el-radio :label="1">满</el-radio>
            <el-radio :label="2">未满</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <el-row style="text-align: center">
        <el-popover
                placement="top"
                width="300"
                trigger="click"

        >
          <el-table
                  :data="room.stu"
                  size="mini"
                  style="width: 100%"
                  tooltip-effect="dark"
          >
            <el-table-column  property="chStuNo" label="学号"></el-table-column>
            <el-table-column  property="chStuName" label="姓名"></el-table-column>
            <el-table-column  property="chBed" label="床位"></el-table-column>
          </el-table>
          <el-button slot="reference">居住学生详情</el-button>
        </el-popover>
      </el-row>

      <span slot="footer" class="dialog-footer">
        <el-button @click="roomVisible = false">取 消</el-button>
        <el-button @click="roomVisible = false" type="primary">确 定</el-button>
      </span>
    </el-dialog>
  </el-main>
</template>

<script>
export default {
  created() {
    this.sel(1);
  },
  methods: {
    sel(pageNo){
      this.$axios.get("api/history/select",{
        params:{
          hisStuName: this.selectHistoryFrom.hisStuName,
          hisStuNo: this.selectHistoryFrom.hisStuNo,
          hisBld: this.selectHistoryFrom.hisBld,
          hisRoom: this.selectHistoryFrom.hisRoom,
          hisReason: this.selectHistoryFrom.hisReason,
          pageSize: 10,
          currentPage: pageNo,
        }
      })
    .then(res => {
      if (res.data.code == 200) {
        console.log(res);
        this.historyTable = res.data.data.records;
        this.page.totalCount=res.data.data.total;
        this.page.pageSize=res.data.data.size;
        this.page.currentPage=res.data.data.current;
      }
      })
    },
    selectRoom(row){
        this.roomVisible=true;
        this.$axios({
          method: "get",
          url: "api/room/room",
          params:{
            chBld:row.hisBld,
            chRoom:row.hisRoom
          }
        }).then((result) => {
          this.$axios({
            method: "get",
            url: "api/room/oneRoom",
            params:{
              chBld:row.hisBld,
              chRoom:row.hisRoom
            }
          }).then((res) => {
            this.room={
              roomNo: res.data.roomNo,
              roomBld: res.data.roomBld,
              roomAddress: res.data.roomAddress,
              roomType: res.data.roomType,
              roomState: res.data.roomState,
              roomCost:res.data.roomCost,
              stu: result.data
            };
            console.log(this.room);
          })
        })
    },
    handleEdit(row){
      console.log(row);
      this.$axios({
        method: "delete",
        url: "api/history/del",
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        },
        data: JSON.stringify([row.hisId]),
      }).then(res => {
        if (res.data.code == 200){
          this.$message({
            type: "success",
            message: res.data.msg
          })
          if (((this.page.totalCount-1)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
            this.sel(this.page.currentPage-1);
          } else {
            this.sel(this.page.currentPage);
          }
        } else {
          this.$message({
            type: "error",
            message: res.data.msg
          })
        }
      })
    },
    del() {
      this.$confirm('是否删除所选信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        let ids = [];
        this.tableChecked.forEach(id => {
          ids.push(id.hisId);
        });
        this.$axios({
          method: "delete",
          url: "api/history/del",
          headers: {
            'Content-Type': 'application/json;charset=UTF-8'
          },
          data: JSON.stringify(ids),
        }).then(res => {
          if (res.data.code == 200){
            this.$message({
              type: "success",
              message: res.data.msg
            })
            if (((this.page.totalCount-ids.length)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
              this.sel(this.page.currentPage-1);
            } else {
              this.sel(this.page.currentPage);
            }
          } else {
            this.$message({
              type: "error",
              message: res.data.msg
            })
          }
        })
      })
    },
    StateFormat(row) {
      if (row.hisState == 0) {
        return "入住";
      } else if (row.hisState == 1) {
        return "退宿";
      } else {
        return "调宿";
      }
    },
    IsdeleteFormat(row){
      if(row.hisIsdelete==0){
        return "否";
      }
      return "是";
    },
    //表格前的复选框点击事件
    handleSelectionChange(val) {
      this.tableChecked = val;
    },
    //pageSize改变调用
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 页数改变调用
    handleCurrentChange(val) {
      this.sel(val);
    },
  },
  data() {
    return {
      roomVisible:false,
      room:{
        roomNo: "",
        roomBld: '',
        roomAddress: '',
        roomType: '',
        roomState: '',
        roomCost:'',
        stu:[],
      },
      tableHeight: 0,
      tableChecked:[],
      selectHistoryFrom: {
        hisStuName: "",
        hisStuNo: "",
        hisBld:'',
        hisRoom: "",
        hisReason: "",
      },
      historyTable: [],
      page: {
        currentPage: 1, // 当前页
        pageSize: 10, // 每页显示条目个数
        totalCount: 0 // 总条目数
      },
    };
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