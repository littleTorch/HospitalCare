<template>
    <el-main>
    <el-form size="mini" label-width="80px" style="margin-bottom: 20px">
      <el-row>
        <el-button @click="addSchool" size="mini" type="primary" icon="el-icon-plus">新增校区</el-button>
        <el-button @click="deleteList(tableChecked)" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>
      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table
            :data="schoolData.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
            ref="schoolData"
            size='mini'
            @selection-change="handleSelectionChange"
            :height="tableHeight"
            border
            style="width: 100%">
      <el-table-column  align="center" type="selection" width="43"></el-table-column>
      <el-table-column align="center" prop="schName" label="校区名称"></el-table-column>
      <el-table-column align="center" prop="schAddress" label="校区地址" ></el-table-column>
      <el-table-column align="center" prop="schParent" label="所属" :formatter="parentFormat"></el-table-column>
      <el-table-column label="操作" width="450" align="center">
        <template slot-scope="scope">
          <el-button @click="details(scope.row.schId)" type="primary" size="mini">查看院系</el-button>
          <el-button @click="parent(scope.row.schId)" type="primary" size="mini" :disabled="scope.row.schParent == 0?true:false">设置为子校区</el-button>
          <el-button @click="update(scope.row)" type="success" size="mini">编辑</el-button>
            <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定删除该校区吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="deleteOne(scope.row.schId)">
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
      :total="schoolData.length"
    ></el-pagination>

<!--        查看院系-->
    <el-dialog title="所属院系" :visible.sync="colVisiable" width="20%">
        <el-table
                :data="colData"
                size="mini"
                style="width: 100%"
                tooltip-effect="dark">
            <el-table-column align="center" property="collegeNo" label="院系编号"></el-table-column>
            <el-table-column align="center" property="collegeName" label="院系名称"></el-table-column>
        </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="colVisiable = false">返 回</el-button>
      </span>
    </el-dialog>

    <!--添加校区-->
    <el-dialog title="添加校区" :visible.sync="addVisible" width="25%">
            <el-form
                    align="center"
                    class="addOne"
                    ref="addOne"
                    size="mini"
                    :model="addOne"
                    :inline="true"
                    label-width="100px">
                <el-form-item label="校区名称" prop="schName">
                    <el-input v-model="addOne.schName" placeholder="请输入新校区名称"></el-input>
                </el-form-item>
                <el-form-item label="校区地址" prop="schAddress">
                    <el-input v-model="addOne.schAddress" placeholder="请输入新校区地址"></el-input>
                </el-form-item>
                <br/>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="addSubmit()" type="primary" >确 定</el-button>
        <el-button @click="addVisible = false">取消</el-button>
      </span>
        </el-dialog>

    <!--修改校区-->
    <el-dialog title="修改校区信息" :visible.sync="updateVisible" width="25%">
            <el-form
                    class="updateData"
                    ref="updateData"
                    size="mini"
                    style="text-align:center"
                    :model="updateData"
                    :inline="true"
                    label-width="100px">
                <el-form-item label="校区名称">
                    <el-input v-model="updateData.schName" placeholder="请输入校区名称"></el-input>
                </el-form-item>
                <el-form-item label="校区地址">
                    <el-input v-model="updateData.schAddress"  placeholder="请输入校区地址"></el-input>
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
  created: function(){
    this.getSchoolList();
  },
  methods: {
      addSchool(){
          this.addVisible = true;
      },
      details(id){
          // alert(id);
          this.getCollegeList(id);
          this.colVisiable = true;
      },
      update(row){
          this.updateData = row;
          this.updateVisible = true;
      },

      getSchoolList(){
          this.$axios({
              methods: "get",
              url: "api/school/schoolList",
          }).then((result)=>{
              this.schoolData = result.data;
              console.log(this.schoolData);
          })
      },
      getCollegeList(id){
          this.$axios({
              methods: "get",
              url: "api/colleges/selectBySchool",
              params:{
                  collegeSchool: id,
              }
          }).then((result)=>{
              this.colData = result.data;
              console.log(this.colData);
          })
      },

      addSubmit(){
          this.$refs.addOne.validate((valid)=>{
              if(valid){
                  var data = new FormData();
                  data.append('schName',this.addOne.schName);
                  data.append('schAddress',this.addOne.schAddress);
                  this.$axios({
                      method: "post",
                      url: "api/school/insertOne",
                      data: data,
                  }).then(()=>{
                      alert("添加成功");
                      this.addVisible = false;
                      this.$refs.addOne.resetFields();
                      this.getSchoolList();
                  })
              }
          })
      },

      updateSubmit(){
          this.$refs.updateData.validate((valid)=>{
              if(valid){
                  var data = new FormData();
                  data.append('schId',this.updateData.schId);
                  data.append('schName',this.updateData.schName);
                  data.append('schAddress',this.updateData.schAddress);
                  this.$axios({
                      method: "post",
                      url: "api/school/update",
                      data: data,
                  }).then(()=>{
                      alert("修改成功");
                      this.updateVisible = false;
                      this.getSchoolList();
                  })
              }
          })
      },
      parent(id){
          this.$confirm('是否将该校区划分为子校区','提示',{
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
          }).then(()=>{
              var data = new FormData();
              data.append('schId',id);
              data.append('schParent',0);
              this.$axios({
                  method: "post",
                  url: "api/school/update",
                  data: data,
              }).then(()=>{
                  alert("设置成功");
                  this.getSchoolList();
              })
          })
      },

      deleteOne(id){
          this.$axios({
              method: "get",
              url: "api/school/deleteOne",
              params: {
                  schId: id,
              }
          }).then(()=>{
              alert("删除成功！");
              location.reload();
          })
      },
      deleteList(){
          if(this.tableChecked.length ===0){
              alert("您未选择任一校区");
          }else {
              this.$confirm('是否删除所选校区信息?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning',
              }).then(() => {
                  this.tableChecked.forEach(id => {
                      this.ids.push(id.schId);
                  });
                  console.log(this.ids);
                  this.$axios({
                      method: "delete",
                      url: "api/school/delete",
                      headers: {
                          'Content-Type': 'application/json;charset=UTF-8'
                      },
                      data: JSON.stringify(this.ids),
                  }).then(() => {
                      alert("删除成功！");
                      location.reload();
                  })
              })
          }
      },

      handleSelectionChange(val){
          this.tableChecked = val;
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
    parentFormat(row){
        if(row.schParent == 0){
            return "子校区";
        }else{
            return "主校区";
        }
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 280;
    });
  },
  data() {
    return {
      pageSize:10,
      currentPage:1,
        colVisiable: false,
        addVisible: false,
        updateVisible: false,
      // 表格高度
      tableHeight: window.innerHeight,
      // 表格数据
      schoolData: [],
      colData:[],
      tableChecked:[],
        addOne: {},
        updateData: [],
        ids: [],
    };
  },
};
</script>

<style>
</style>