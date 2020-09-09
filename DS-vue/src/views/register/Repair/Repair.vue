<template>
    <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="selectFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="名称:">
            <el-input v-model="selectFrom.repName" placeholder="请输入申请人姓名"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="select(selectFrom)" style="margin-left: 20px;" class="btn-left" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addRepVisible = true" size="mini" type="primary" icon="el-icon-plus">设施维护申请</el-button>
        <el-button @click="deleteList(tableChecked)" size="mini" type="primary" icon="el-icon-delete" >批量撤销</el-button>
        <el-button @click="download" size="mini" type="primary" icon="el-icon-download">导出设施维护记录</el-button>
      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table
            :data="repData.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
            ref="repData"
            @selection-change="handleSelectionChange"
            size='mini'
            :height="tableHeight"
            border
            style="width: 100%">
      <el-table-column  align="center" type="selection" ></el-table-column>
      <el-table-column align="center" prop="repName" label="申请人名字" ></el-table-column>
      <el-table-column align="center" prop="repBld" label="宿舍楼" ></el-table-column>
      <el-table-column align="center" prop="repRoom" label="房间号" ></el-table-column>
      <el-table-column align="center" :formatter="stateFormat"  prop="repState" label="当前状态"></el-table-column>
      <el-table-column label="操作" width="350" align="center">
        <template slot-scope="scope">
          <el-button @click="state(scope.row)" type="primary" size="mini" :disabled="scope.row.repState == 0?false:true">维护</el-button>
          <el-button @click="details(scope.row)" type="primary" size="mini">详情</el-button>
          <el-button @click="updateRep(scope.row)" type="success" size="mini" :disabled="scope.row.repState == 0?false:true">编辑</el-button>
          <el-popconfirm
                  style="margin-left: 10px;"
                  title="您确定要删除这条记录吗？"
                  icon="el-icon-info"
                  iconColor="red"
                  @onConfirm="deleteOne(scope.row.repId)">
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
      :page-size="pageSize"
      layout="total, prev, pager, next"
      :total="repData.length"
    ></el-pagination>

<!--    设施维护申请    -->
    <el-dialog title="设施维护申请" :visible.sync="addRepVisible" width="30%">
       <el-form
               class="addData"
               ref="addData"
               size="mini"
               style="text-align:center"
               :model="addData"
               :inline="true"
               label-width="100px">
        <el-form-item label="申请人名字" prop="repName">
          <el-input v-model="addData.repName" placeholder="请输入申请人名字"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼" prop="repBld">
          <el-input v-model="addData.repBld" placeholder="请输入宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="宿舍号" prop="repRoom">
<!--          <el-input v-model="addData.repRoom" placeholder="请输入宿舍号"></el-input>-->
            <el-select v-model="addData.repRoom" placeholder="请选择房间号">
                <el-option
                        v-for="item in room"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="维护设备详情" prop="repDetails">
          <el-input v-model="addData.repDetails" type="textarea" placeholder="请输入维护设备详情"></el-input>
        </el-form-item>
        <br/>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addRepVisible = false">取 消</el-button>
        <el-button @click="addSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

<!--        修改设施维护申请-->
    <el-dialog title="修改设施维护申请" :visible.sync="updateRepVisible" width="30%">
       <el-form
               class="updateData"
               ref="updateData"
               size="mini"
               style="text-align:center"
               :model="updateData"
               :inline="true"
               label-width="100px">
        <el-form-item label="申请人名字">
          <el-input v-model="updateData.repName" placeholder="请输入申请人名字"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼">
          <el-input v-model="updateData.repBld" placeholder="请输入宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="宿舍号">
<!--          <el-input v-model="updateData.repRoom" placeholder="请输入宿舍号"></el-input>-->
            <el-select v-model="updateData.repRoom" placeholder="请选择房间号">
                <el-option
                        v-for="item in room"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="维护设备详情">
          <el-input v-model="updateData.repDetails" type="textarea" placeholder="请输入维护设备详情"></el-input>
        </el-form-item>
        <br/>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateRepVisible = false">取 消</el-button>
        <el-button @click="updateSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

    <!--详情模态框-->
    <el-dialog title="设施维护申请详情" :visible.sync="repDetailsVisible" width="30%">
        <el-form
                class="repDetails"
                ref="repDetails"
                size="mini"
                style="text-align:center"
                :model="repDetails"
                :inline="true"
                label-width="100px">
            <el-form-item label="申请人姓名">
                <el-input  v-model="repDetails.repName" disabled></el-input>
            </el-form-item>
            <el-form-item label="宿舍楼">
                <el-input  v-model="repDetails.repBld" disabled></el-input>
            </el-form-item>
            <el-form-item label="房间号">
                <el-input  v-model="repDetails.repRoom" disabled></el-input>
            </el-form-item>
            <el-form-item label="维护设备详情">
                <el-input  v-model="repDetails.repDetails" type="textarea" disabled></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="repDetailsVisible = false">返 回</el-button>
        </span>
    </el-dialog>

    </el-main>
</template>

<script>

export default {
  created: function(){
    this.getRepData();
    this.roomNoList();
  },
  methods: {
    details(row){
        this.repDetails = row;
        this.repDetailsVisible = true;
     },
    updateRep(row){
      this.updateData = row;
      this.updateRepVisible = true;
    },
    getRepData(){
        this.$axios({
            methods: "get",
            url: "api/repair/repairList"
        }).then((result) =>{
            this.repData = result.data;
            // console.log(this.repData);
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
                      value:roomList[i].roomBld+roomList[i].roomNo,
                      lable:roomList[i].roomNo,
                  };
                  this.room.push(roomNo);
              }
          })
      },

    select(row){
        if(row.repName == null){
            this.getRepData();
        }else {
            this.$axios({
                method: "get",
                url: "api/repair/selectByFrom",
                params: {
                    repName: row.repName,
                }
            }).then((result) =>{
                this.repData = result.data;
                // console.log(this.repData);
            })
        }

    },

    addSubmit(){
        this.$refs.addData.validate((valid) =>{
            if(valid){
                var data = new FormData();
                data.append('repName',this.addData.repName);
                data.append('repBld',this.addData.repBld);
                data.append('repRoom',this.addData.repRoom);
                data.append('repDetails',this.addData.repDetails);
                this.$axios({
                    method: "post",
                    url: "api/repair/insertRepair",
                    data: data,
                }).then(()=>{
                    alert("添加申请成功！");
                    this.addRepVisible = false;
                    this.$refs.addData.resetFields();
                    this.getRepData();
                })
            }
        });
    },

    updateSubmit(){
        this.$refs.updateData.validate((valid)=>{
            if(valid){
                var data = new FormData();
                data.append('repId',this.updateData.repId);
                data.append('repName',this.updateData.repName);
                data.append('repBld',this.updateData.repBld);
                data.append('repRoom',this.updateData.repRoom);
                data.append('repDetails',this.updateData.repDetails);
                this.$axios({
                    method: "post",
                    url: "api/repair/updateRepair",
                    data: data,
                }).then(()=>{
                    alert("修改成功！")
                    this.updateRepVisible = false;
                    this.getRepData();
                }).catch(()=>{
                    alert("修改失败！")
                    this.updateRepVisible = false;
                    this.getRepData();
                })
            }
        })
    },
    state(row){
        var data = new FormData();
        data.append('repId',row.repId);
        data.append('repState',1);
        this.$confirm('是否已进行维护？','提示',{
            confirmButtonText:'确定',
            cancelButtonText: '取消',
            type: 'warning',
        }).then(()=>{
            this.$axios({
                method: "post",
                url: "api/repair/updateRepair",
                data: data,
            }).then(()=>{
                alert("维护操作成功");
                this.getRepData();
            })
        })
    },

    deleteOne(id){
        this.$axios({
            method: "delete",
            url: "api/repair/deleteOne",
            params: {
                repId: id,
            }
        }).then(()=>{
            alert("已删除该申请！");
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
                    this.ids.push(id.repId);
                });
                this.$axios({
                    method: "delete",
                    url: "api/repair/delete",
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
    download(){
        this.$axios({
            url: "api/repair/download",
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

    stateFormat(row){
          if(row.repState == 0){
              return "申请维护中"
          }else{
              return "已维护"
          }
      },
    //获取选项框的值
    handleSelectionChange (val){
        //console.log(val);
      this.tableChecked = val;
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
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 285;
    });
  },
  data() {
    return {
      addRepVisible: false,
      updateRepVisible: false,
      repDetailsVisible: false,
      pageSize:5,
      currentPage:1,
      addData: [],
      //搜索框数据绑定
      selectFrom: {},
      updateData: [],
      // 表格高度
      tableHeight: window.innerHeight,
      // 表格数据
      repData: [],
      tableChecked:[],
      repDetails:{},
      ids: [],
      room:[],
    };
  },
};
</script>

<style>
</style>