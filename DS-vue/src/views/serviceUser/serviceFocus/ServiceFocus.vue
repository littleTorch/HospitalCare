<template>
  <el-container>
    <el-header style="padding:20px 10px 10px 10px;text-align: center">
      <el-form size="mini" :inline="true" :model="serviceFocusForm" class="demo-form-inline">
        <el-row>
          <el-col span="">
            <el-form-item label="客户名" prop="serviceFocus">
              <el-input v-model="serviceFocusForm.cusName" placeholder="请输入要查询的客户名"></el-input>
            </el-form-item>
            <el-button @click="selectByLike" size="mini" type="primary" round>查询</el-button>
            <el-button @click="addOpen" size="mini" type="success" round>添加</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-header>
    <el-main>
      <!--数据表格-->
      <el-table
              :data="serviceFocusData"
              :height="tableHeight"
              size="mini"
              border
              style="width: 100%"
      >
        <el-table-column align="center" prop="cusName" label="客户名"></el-table-column>
        <el-table-column align="center" prop="projectName" label="护理项目"></el-table-column>
        <el-table-column align="center" prop="serveStart" label="服务购买日期" sortable></el-table-column>
        <el-table-column align="center" prop="serveEnd" label="服务到期日期" sortable></el-table-column>
        <el-table-column align="center" prop="elderType" :formatter="elderTypeF" label="老人类型"></el-table-column>
        <el-table-column align="center" prop="remark" label="备注"></el-table-column>
        <el-table-column label="操作" width="300" align="center">
          <template slot-scope="scope">
            <el-button @click="details(scope.row)" type="primary" size="mini">详情</el-button>
            <el-button @click="buyOpen(scope.row)" type="success" size="mini">购买</el-button>
            <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定删除吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="delServiceFocus(scope.row.id)"
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

      <el-dialog title="添加服务关注" :visible.sync="serviceFocusAddVisible" width="40%">
        <el-form
                :model="serviceFocusAdd"
                size="mini"
                style="text-align:left"
                :inline="true"
                label-width="100px"
                ref="serviceFocusAdd"
                :rules="rules"
        >
          <el-row>
            <el-form-item label="客户名" prop="id">
<!--              <el-input v-model="serviceFocusAdd.cusName" placeholder="请输入客户名"></el-input>-->
              <el-select v-model="serviceFocusAdd.id" placeholder="请选择">
                <el-option
                        v-for="item in this.customerList"
                        :key="item.id"
                        :label="item.cusName"
                        :value="item.id"
                        :disabled="item.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="护理项目" prop="projectId">
              <el-select v-model="serviceFocusAdd.projectId" placeholder="请选择">
                <el-option
                        v-for="item in this.projectNames"
                        :key="item.id"
                        :label="item.projectName"
                        :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
              <el-form-item label="服务购买时长" prop="duration" label-width="1">
                <el-date-picker
                        v-model="serviceFocusAdd.duration"
                        type="datetimerange"
                        :picker-options="pickerOptions"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        align="right">
                </el-date-picker>
              </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="老人类型" prop="elderType">
              <el-select v-model="serviceFocusAdd.elderType" placeholder="请选择">
                <el-option
                        v-for="item in elderTypeS"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="备注">
              <el-input v-model="serviceFocusAdd.remark"></el-input>
            </el-form-item>
          </el-row>
        </el-form>

        <span slot="footer" class="dialog-footer">
        <el-button @click="serviceFocusAddVisible = false">取 消</el-button>
        <el-button @click="addSumit">确定</el-button>
      </span>
      </el-dialog>

      <el-dialog title="服务关注详情" :visible.sync="serviceFocusDetailsVisible" width="40%">
        <el-form
                :model="serviceFocusDetails"
                size="mini"
                style="text-align:center"
                :inline="true"
                label-width="100px"
                disabled
        >
          <el-form-item label="服务关注id">
            <el-input v-model="serviceFocusDetails.id"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-input v-model="serviceFocusDetails.createTime"></el-input>
          </el-form-item>
          <el-form-item label="创建者">
            <el-input v-model="serviceFocusDetails.createBy"></el-input>
          </el-form-item>
          <el-form-item label="更新时间">
            <el-input v-model="serviceFocusDetails.updateTime"></el-input>
          </el-form-item>
          <el-form-item label="更新者">
            <el-input v-model="serviceFocusDetails.updateBy"></el-input>
          </el-form-item>
          <el-form-item label="护理项目id">
            <el-input v-model="serviceFocusDetails.projectId"></el-input>
          </el-form-item>
          <el-form-item label="护理项目">
            <el-input v-model="serviceFocusDetails.projectName"></el-input>
          </el-form-item>
          <el-form-item label="客户id">
            <el-input v-model="serviceFocusDetails.cusId"></el-input>
          </el-form-item>
          <el-form-item label="客户名">
            <el-input v-model="serviceFocusDetails.cusName"></el-input>
          </el-form-item>
          <el-form-item label="服务购买日期">
            <el-input v-model="serviceFocusDetails.serveStart"></el-input>
          </el-form-item>
          <el-form-item label="服务到期日期">
            <el-input v-model="serviceFocusDetails.serveEnd"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="serviceFocusDetails.remark"></el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
        <el-button @click="serviceFocusDetailsVisible = false">取 消</el-button>
      </span>
      </el-dialog>

      <el-dialog title="购买服务" :visible.sync="serviceFocusUpdateVisible" width="40%">
        <el-form
                :model="serviceFocusUpdateData"
                ref="serviceFocusUpdateData"
                size="mini"
                style="text-align:left"
                :inline="true"
                label-width="100px"
                :rules="rules"
        >
          <el-row>
            <el-form-item label="护理项目" prop="projectId">
              <el-select v-model="serviceFocusUpdateData.projectId" placeholder="请选择">
                <el-option
                        v-for="item in projectNames"
                        :key="item.id"
                        :label="item.projectName"
                        :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>

          <el-row>
            <el-form-item label="服务购买时长" prop="duration" label-width="1">
              <el-date-picker
                      v-model="serviceFocusUpdateData.duration"
                      type="datetimerange"
                      :picker-options="pickerOptions"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      align="right">
              </el-date-picker>
            </el-form-item>
          </el-row>

        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="serviceFocusUpdateVisible = false">取 消</el-button>
          <el-button @click="buyProject()" type="primary">购买</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>

  import qs from "qs";

  export default {
    name: "ServiceFocus",
    methods: {
      elderTypeF(row){
        switch (row.elderType) {
          case "0":return "普通老人";
          case "1":return "残疾老人";
          default:return row.elderType;
        }
      },
      dateFormat(fmt, date) {
        let ret;
        const opt = {
          "y+": date.getFullYear().toString(),        // 年
          "M+": (date.getMonth() + 1).toString(),     // 月
          "d+": date.getDate().toString(),            // 日
          "H+": date.getHours().toString(),           // 时
          "m+": date.getMinutes().toString(),         // 分
          "s+": date.getSeconds().toString()          // 秒
          // 有其他格式化字符需求可以继续添加，必须转化成字符串
        };
        for (let k in opt) {
          ret = new RegExp("(" + k + ")").exec(fmt);
          if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
          };
        };
        return fmt;
      },

      dataToTimeStamp(dateEnd){
        let date = String(dateEnd).substring(0,19);
        date = date.replace(/-/g,'/');
        let timestamp = new Date(date).getTime();
        return timestamp;
      },

      selectByLike() {
        if (this.serviceFocusForm.cusName==null||this.serviceFocusForm.cusName=="")return;
        this.isSelectByLike = true;
        this.$axios('api/serviceFocus/selectByLike', {
          params: {
            cusName: this.serviceFocusForm.cusName
          }
        }).then(res => {
          this.serviceFocusData = res.data.data;
          this.page.totalCount = res.data.data.length;
        });
      },

      async addOpen(){
        await this.$axios("api/serviceFocus/getCustomerList").then(res => {
          this.customerList = res.data;
        });

        this.serviceFocusData.forEach(x => {
          this.customerList.forEach(y => {
            if (y.id == x.cusId){
              y.disabled = true;
            }
          })
        });

        this.$axios("api/serviceFocus/getNurseProjectList").then(res => {
          this.projectNames = res.data;
        });

        this.serviceFocusAddVisible = true;
      },

      addSumit(){
        let _this = this;
        this.customerList.forEach(x => {
          if (x.id == _this.serviceFocusAdd.id){
            _this.serviceFocusAdd.cusName = x.cusName;
          }
        })
        this.serviceFocusData.forEach(x => {
          if (x.projectId == _this.serviceFocusAdd.projectId){
            _this.serviceFocusAdd.projectName = x.projectName;
          }
        })
        // console.log(JSON.stringify(this.serviceFocusAdd));return;
        this.$refs.serviceFocusAdd.validate((valid) => {
          if (valid) {
            this.$axios("api/serviceFocus/addServiceFocus", {
              params: {
                createBy: this.serviceFocusAdd.createBy,
                cusId: this.serviceFocusAdd.id,
                cusName: this.serviceFocusAdd.cusName,
                projectId: this.serviceFocusAdd.projectId,
                projectName: this.serviceFocusAdd.projectName,
                serveStart: this.dateFormat("yyyy-MM-dd HH:mm:ss", this.serviceFocusAdd.duration[0]),
                // serveStart: this.serviceFocusAdd.duration[0],
                serveEnd: this.dateFormat("yyyy-MM-dd HH:mm:ss", this.serviceFocusAdd.duration[1]),
                // serveEnd: this.serviceFocusAdd.duration[1],
                elderType: this.serviceFocusAdd.elderType,
                remark: this.serviceFocusAdd.remark,
              }
            }).then((result) => {
              if (result.data.code == 200) {
                this.$message({
                  type: "success",
                  message: result.data.msg,
                });
                this.serviceFocusAddVisible = false;
                if (((this.page.totalCount - 1) % this.page.pageSize == 0) && (this.page.currentPage != 1)) {
                  this.getTableData(this.page.currentPage - 1);
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
          }
        })
      },

      details(row){
        this.serviceFocusDetailsVisible = true;
        this.serviceFocusDetails = row;
      },

      buyOpen(row) {
        this.serviceFocusUpdateData = row;
        // console.log(JSON.stringify(row));
        this.serviceFocusUpdateData.duration = [row.serveStart, row.serveEnd];
        this.$axios("api/serviceFocus/getNurseProjectList").then(res => {
          this.projectNames = res.data;
        });
        this.serviceFocusUpdateVisible = true;
      },

      buyProject(){
        this.$refs.serviceFocusUpdateData.validate(async valid => {
          if (valid){
            await this.$axios("api/serviceFocus/getProjectNameByProjectId",{
              params: {
                projectId: this.serviceFocusUpdateData.projectId
              }
            }).then(res => {
              this.serviceFocusUpdateData.projectName = res.data;
            })
            // this.serviceFocusUpdateData.serveStart = this.dateFormat("yyyy-MM-dd HH:mm:ss",this.serviceFocusUpdateData.duration[0]);
            this.serviceFocusUpdateData.serveStart = this.serviceFocusUpdateData.duration[0];
            // this.serviceFocusUpdateData.serveEnd = this.dateFormat("yyyy-MM-dd HH:mm:ss",this.serviceFocusUpdateData.duration[1]);
            this.serviceFocusUpdateData.serveEnd = this.serviceFocusUpdateData.duration[1];
            // console.log(JSON.stringify(this.serviceFocusUpdateData));return;
            this.$axios.post("api/serviceFocus/buyProject",  qs.stringify(this.serviceFocusUpdateData)).then(res => {
              if (res.data.code == 200){
                this.$message({
                  type: "success",
                  message: res.data.msg
                })
                this.serviceFocusUpdateVisible = false;
                this.getTableData(this.page.currentPage);
              } else {
                this.$message({
                  type: "error",
                  message: res.data.msg
                })
              }
            })
          }
        });
      },

      delServiceFocus(id) {
        this.$axios({
          method: 'delete',
          url: 'api/serviceFocus/delServiceFocus',
          params: {
            id: id
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
        self.$axios('api/serviceFocus/getServiceFocusList', {
          params: {
            pageSize: 10,
            currentPage: pageNo
          }
        }).then(res => {
          if (res.data.code == 200) {
            self.serviceFocusData = res.data.data.records;
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
    },
    data(){
      return {
        //表单验证
        rules: {
          id: [
            { required: true, message: "客户名不能为空", trigger: "blur" },
          ],
          projectId: [
            { required: true, message: "护理项目不能为空", trigger: "blur" },
          ],
          projectName: [
            { required: true, message: "护理项目不能为空", trigger: "blur" },
          ],
          duration: [
            { required: true, message: "服务购买时长不能为空", trigger: "blur" },
          ],
          elderType: [
            { required: true, message: "老人类型不能为空", trigger: "blur" },
          ],
        },

        customerList: [],

        pickerOptions: {
          shortcuts: [{
            text: '一个月',
            onClick(picker) {
              const start = new Date();
              const end = new Date();
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '三个月',
            onClick(picker) {
              const start = new Date();
              const end = new Date();
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 30 * 3);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '半年',
            onClick(picker) {
              const start = new Date();
              const end = new Date();
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 30 * 6);
              picker.$emit('pick', [start, end]);
            }
          },{
            text: '一年',
            onClick(picker) {
              const start = new Date();
              const end = new Date();
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 365);
              picker.$emit('pick', [start, end]);
            }
          }],
        },

        duration: '',


        // 表格高度
        tableHeight: window.innerHeight,
        isSelectByLike: false,
        page: {
          currentPage: 1, // 当前页
          pageSize: 10, // 每页显示条目个数
          totalCount: 0 // 总条目数
        },
        serviceFocusForm:{
          cusName: "",
        },
        isServeEndFlag: false,
        serviceFocusData: [],
        serviceFocuss: [],
        serviceFocusAdd: {
          createBy: JSON.parse(sessionStorage.getItem("user")).userName,
          cusName: "",
          projectName: "",
          duration: ""
        },
        serviceFocusDetails: [],
        serviceFocusUpdateData: [],
        serviceFocusAddVisible: false,
        serviceFocusDetailsVisible: false,
        serviceFocusUpdateVisible: false,
        isNoneBuy: false,
        projectNames: [],
        user: {
          userId: JSON.parse(sessionStorage.getItem("user")).userId,
          userName: JSON.parse(sessionStorage.getItem("user")).userName
        },
        elderTypeS:[
          {
            value: 0,
            label: "普通老人"
          },
          {
            value: 1,
            label: "残疾老人"
          }
        ]
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
  }
</script>

<style scoped>

</style>