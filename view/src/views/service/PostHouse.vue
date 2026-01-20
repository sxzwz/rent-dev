<template>
  <div class="house-container">
    <div class="nav">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item style="cursor: pointer;" @click.native="toLastPage">
          <span style="color: rgb(51,51,51);font-size: 18px;">房源管理</span>
        </el-breadcrumb-item>

        <el-breadcrumb-item>
          <span style="color: rgb(51,51,51);font-size: 18px;"
            >新增房屋信息</span
          >
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 房屋信息区域 -->
    <div class="update-area">
      <!-- 房屋名称 -->
      <div class="area">
        <span>房屋名</span>

        <el-input
          style="width: 600px;"
          v-model="house.name"
          placeholder="请输入，30个字以内"
        ></el-input>
      </div>

      <!-- 省份信息 -->
      <div class="area">
        <span>所属位置</span>

        <div style="display: flex;gap: 20px;">
          <el-select
            @change="handleAreaChange"
            style="width: 290px;"
            v-model="topAreaId"
            placeholder="请选择"
          >
            <el-option
              v-for="item in topArea"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>

          <el-select
            @change="fetchCommunity"
            style="width: 290px;"
            v-model="cityAreaId"
            placeholder="请选择"
          >
            <el-option
              v-for="item in cityArea"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
      </div>

      <!-- 封面 -->
      <div class="area">
        <span>封面</span>

        <div class="user-avatar">
          <p style="font-size: 12px;color: rgb(0, 119, 184);">
            点击📷处即可上传房屋封面
          </p>

          <img
            v-if="cover"
            style="width: 290px;height: 170px;border-radius: 5px;"
            :src="cover || ''"
            alt=""
          />
          <el-upload
            class="avatar-uploader"
            action="file/upload"
            :show-file-list="false"
            :on-success="handleImageSuccess"
          >
            <i class="el-icon-camera-solid"></i>
          </el-upload>
        </div>
      </div>

      <!-- 实况图 -->
      <div class="area">
        <span>实况图</span>

        <div>
          <el-upload
            :file-list="coverList"
            :on-success="handleCovers"
            action="file/upload"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
          >
            <i class="el-icon-plus"></i>
          </el-upload>

          <el-dialog :modal="false" :visible.sync="dialogVisible">
            <img
              style="z-index: 1000;"
              width="100%"
              :src="dialogImageUrl"
              alt=""
            />
          </el-dialog>
        </div>
      </div>

      <!-- 房屋介绍 -->
      <div class="area">
        <span>房屋介绍</span>

        <div style="background-color: rgba(35, 170, 242,0.2);padding: 1px;">
          <Editor
            style="width: 600px;"
            :receiveContent="content"
            height="300px"
            api="file/upload"
            @on-listener="onListener"
          />
        </div>
      </div>

      <!-- 所属小区 -->
      <div class="area">
        <span>所属小区</span>

        <div style="display: flex;gap: 20px;">
          <el-select
            @change="fetchCommunity"
            style="width: 290px;"
            v-model="house.communityId"
            placeholder="请选择"
          >
            <el-option
              v-for="item in communityList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
      </div>

      <!-- 房屋类型 -->
      <div class="area">
        <span>房屋类型</span>

        <Tab
          :buttons="houseTypeList"
          :initialActive="house.typeId"
          @change="handleChange"
        />
      </div>

      <!-- 房屋朝向 -->
      <div class="area">
        <span>房屋类型</span>

        <Tab
          :buttons="houseDirectionList"
          :initialActive="house.directionId"
          @change="handleDirectionChange"
        />
      </div>

      <!-- 房屋户型 -->
      <div class="area">
        <span>房屋户型</span>

        <Tab
          :buttons="houseSizedList"
          :initialActive="house.sizedId"
          @change="handleSizedChange"
        />
      </div>

      <!-- 房屋押金方式 -->
      <div class="area">
        <span>房屋押金方式</span>

        <Tab
          :buttons="houseDepositMethodList"
          :initialActive="house.depositMethodId"
          @change="handleSizedChange"
        />
      </div>

      <!-- 房屋是否临近地铁 -->
      <div class="area">
        <span>房屋是否临近地铁</span>

        <Tab
          :buttons="houseSubwayList"
          :initialActive="house.isSubway"
          @change="handleSubwayChange"
        />
      </div>

      <div class="area">
        <span>地铁线路</span>

        <el-slider
          style="width: 300px;"
          :max="10"
          v-model="house.subwayLine"
          :step="1"
          show-stops
        >
        </el-slider>
      </div>

      <!-- 房屋装修状态 -->
      <div class="area">
        <span>装修状态</span>

        <Tab
          :buttons="houseFitmentStatusList"
          :initialActive="house.fitmentStatusId"
          @change="handleFitmentStatusChange"
        />
      </div>

      <!-- 房屋租赁方式 -->
      <div class="area">
        <span>租赁方式</span>

        <Tab
          :buttons="houseRentalTypeList"
          :initialActive="house.rentalType"
          @change="handleRentalTypeChange"
        />
      </div>

      <!-- 生活设施项配置 -->
      <div class="area">
        <span>生活设施项配置</span>

        <div class="living">
          <div
            class="living-item"
            v-for="(item, index) in houseLivingFacilityList"
            :key="index"
          >
            <div class="text">{{ item.label }}</div>

            <div>
              <el-switch
                v-model="item.selected"
                active-color="#13ce66"
                inactive-color="#929292"
              >
              </el-switch>
            </div>
          </div>
        </div>
      </div>

      <!--产权面积-->
      <div class="area">
        <span>产权面积</span>

        <div>
          <el-input
            v-model="house.sizeNumber"
            placeholder="产权面积"
          ></el-input>
        </div>
      </div>

      <!--所属楼层-->
      <div class="area">
        <span>所属楼层</span>

        <div style="display: flex;align-items: center;">
          <el-input
            v-model="house.floor"
            placeholder="输入（高/中/低）"
          ></el-input>
        </div>
      </div>

      <!--月租金-->
      <div class="area">
        <span>月租金</span>

        <div>
          <el-input v-model="house.rent" placeholder="¥租金（元）"></el-input>
        </div>
      </div>

      <div class="area">
        <div
          class="info-bt"
          @click="saveHouse"
          style="text-align: center;width: 200px;margin-left: 130px;margin-top: 30px;"
        >
          新增房源信息
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Editor from "@/components/Editor.vue";
import Tab from "@/components/Tab.vue";
export default {
  components: { Editor, Tab },
  name: "CommunityUpdate",
  data() {
    return {
      communityList: [], // 小区数据
      communityInfo: {},
      house: {
        typeId: 1, // 类型
        directionId: 1, // 朝向
        sizedId: 1, // 户型
        depositMethodId: 1, // 租金方式
        isSubway: 1, // 是否临近地铁
        fitmentStatusId: 1, // 装修状态
        rentalType: 1 // 租赁方式
      },
      name: null,
      cover: "",
      dialogImageUrl: "",
      dialogVisible: false,
      coverList: [],
      topArea: [], //省份信息
      cityArea: [], // 市区信息
      topAreaId: null, // 省份信息
      cityAreaId: null, // 城市ID
      content: "",
      houseTypeList: [], // 房屋类型数组
      houseDirectionList: [], // 房屋朝向数组
      houseSizedList: [], // 房屋户型数组
      houseDepositMethodList: [], // 房屋押金方式数组
      houseSubwayList: [], // 房屋临近地铁状态
      houseFitmentStatusList: [], // 房屋装修状态数组
      houseRentalTypeList: [], // 房屋租赁方式数组
      houseLivingFacilityList: [] // 查询房屋生活设施配置项数组
    };
  },
  created() {
    this.fetchTopArea();
    this.fetchHouseType();
    this.fetchHouseDirection();
    this.fetchHouseSized();
    this.fetchHouseDepositMethod();
    this.fetchHouseSubway();
    this.fetchHouseFitmentStatus();
    this.fetchHouseRentalType();
    this.fetchLivingFacilityType();
  },
  methods: {
    // 房屋租赁方式
    handleRentalTypeChange(obj) {
      this.house.rentalType = Number(obj.value);
    },
    // 房屋装修状态
    handleFitmentStatusChange(obj) {
      this.house.fitmentStatusId = Number(obj.value);
    },
    // 房屋是否临近地铁选择
    handleSubwayChange(obj) {
      this.house.isSubway = Number(obj.value);
    },
    // 房屋类型选择
    handleChange(obj) {
      this.house.typeId = Number(obj.value);
    },
    // 朝向选择
    handleDirectionChange(obj) {
      this.house.directionId = Number(obj.value);
    },
    // 户型选择
    handleSizedChange(obj) {
      this.house.sizedId = Number(obj.value);
    },
    async fetchCommunity() {
      try {
        const { data } = await this.$axios.post("/community/list", {
          areaId: this.cityAreaId
        });
        this.communityList = data;
      } catch (error) {
        console.log("查询市区下面的小区信息异常：", error);
      }
    },
    // 查询房屋类型
    async fetchHouseType() {
      try {
        const { data } = await this.$axios.get("/house/houseTypeList");
        this.houseTypeList = data;
      } catch (error) {
        console.log("查询房屋类型异常：", error);
      }
    },
    // 查询房屋朝向
    async fetchHouseDirection() {
      try {
        const { data } = await this.$axios.get("/house/houseDirectionList");
        this.houseDirectionList = data;
      } catch (error) {
        console.log("查询房屋朝向异常：", error);
      }
    },
    // 查询房屋户型
    async fetchHouseSized() {
      try {
        const { data } = await this.$axios.get("/house/houseSizedList");
        this.houseSizedList = data;
      } catch (error) {
        console.log("查询房屋户型异常：", error);
      }
    },
    // 查询房屋押金方式数组
    async fetchHouseDepositMethod() {
      try {
        const { data } = await this.$axios.get("/house/houseDepositMethodList");
        this.houseDepositMethodList = data;
      } catch (error) {
        console.log("查询房屋户型异常：", error);
      }
    },
    // 查询房屋临近地铁数组
    async fetchHouseSubway() {
      try {
        const { data } = await this.$axios.get("/house/houseSubwayList");
        this.houseSubwayList = data;
      } catch (error) {
        console.log("查询房屋是否临近地铁异常：", error);
      }
    },
    // 查询房屋装修状态数组
    async fetchHouseFitmentStatus() {
      try {
        const { data } = await this.$axios.get("/house/houseFitmentStatusList");
        this.houseFitmentStatusList = data;
      } catch (error) {
        console.log("查询房屋装修状态异常：", error);
      }
    },
    // 查询房屋租赁方式数组
    async fetchHouseRentalType() {
      try {
        const { data } = await this.$axios.get("/house/houseRentalTypeList");
        this.houseRentalTypeList = data;
      } catch (error) {
        console.log("查询房屋租赁方式异常：", error);
      }
    },
    // 查询房屋生活设施配置项数组
    async fetchLivingFacilityType() {
      try {
        const { data } = await this.$axios.get(
          "/house/houseLivingFacilityList"
        );
        this.houseLivingFacilityList = data;
      } catch (error) {
        console.log("查询房屋生活设施配置项数组异常：", error);
      }
    },
    async saveHouse() {
      try {
        this.house.cover = this.cover;
        this.house.detail = this.content;
        this.house.areaId = this.cityAreaId;
        this.house.covers =
          this.coverList.length === 0
            ? null
            : this.coverList.map(entity => entity.url).join(",");
        this.house.livingFacilities = JSON.stringify(
          this.houseLivingFacilityList
        );
        const { message } = await this.$axios.post("/house/save", this.house);
        this.$notify({
          title: "房屋新增",
          type: "success",
          message: message,
          position: "buttom-right",
          suration: 1000
        });
        this.toLastPage();
      } catch (error) {
        console.log("新增房屋信息异常：", error);
        this.$notify({
          title: "房屋新增",
          type: "info",
          message: error.message,
          position: "buttom-right",
          suration: 1000
        });
      }
    },
    onListener(text) {
      this.content = text;
    },
    async fetchTopArea() {
      try {
        const areaQueryDto = { parentId: 0 };
        const { data } = await this.$axios.post("/area/list", areaQueryDto);
        this.topArea = data;
        this.topAreaId = this.communityInfo.topAreaId;
        this.handleAreaChange();
      } catch (error) {
        console.log("查询省份信息异常：", error);
      }
    },
    async handleAreaChange() {
      try {
        const areaQueryDto = { parentId: this.topAreaId };
        const { data } = await this.$axios.post("/area/list", areaQueryDto);
        this.cityArea = data;
        this.cityAreaId = this.communityInfo.areaId;
      } catch (error) {
        console.log("查询省份下的市区信息异常：", error);
      }
    },
    handleCovers(response, file, fileList) {
      this.coverList.push({
        uid: Date.now() + Math.floor(Math.random() * 1000),
        url: response.data
      });
      console.log("上传，此时的图片数组：", this.coverList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleRemove(file, fileList) {
      console.log("file:", file);

      if (fileList.length === 0) return;
      this.coverList = this.coverList.filter(entity => entity.uid !== file.uid);
    },
    handleImageSuccess(res, file) {
      // 通知提示
      this.$notify({
        title: "封面上传",
        type: res.code === 200 ? "success" : "error",
        message: res.code === 200 ? "上传成功" : res.data,
        position: "buttom-right",
        suration: 1000
      });
      if (res.code === 200) {
        this.cover = res.data; // 响应里面的data，即后端返回的上传后的图片链接
      }
    },
    toLastPage() {
      this.$router.go(-1); // 返回上一页
    }
  }
};
</script>

<style scoped lang="scss">
.living {
  display: flex;
  flex-wrap: wrap;
  background-color: rgb(246, 247, 248);
  width: 800px;

  .living-item {
    display: flex;
    padding: 10px;
    gap: 10px;

    .text {
      font-size: 12px;
    }
  }
}

.update-area {
  padding-block: 10px;
  background-color: rgb(255, 255, 255);
}

.area {
  display: flex;
  justify-content: left;
  align-items: center;
  gap: 10px;
  margin-top: 6px;

  span {
    width: 150px;
    display: inline-block;
    text-align: right;
    margin-right: 10px;
    font-size: 14px;
    color: rgb(51, 51, 51);
  }
}

.house-container {
  box-sizing: border-box;

  .nav {
    background-color: rgb(220, 220, 220);
    padding: 12px 20px;
    border-bottom: 1px solid rgb(230, 230, 230);
  }
}
</style>
