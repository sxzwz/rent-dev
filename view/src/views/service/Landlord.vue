<template>
  <div class="landlord-container">
    <h2>认证信息</h2>

    <div class="auth-status">
      <i :class="landlord.isAudit ? 'el-icon-success' : 'el-icon-warning'"></i>

      {{ landlord.isAudit ? "已审核" : "审核中" }}
    </div>

    <!-- 用户自己的申请信息 -->
    <div class="auth-user-info">
      <img :src="landlord.avatar" alt="" />
      <div>{{ landlord.username }}</div>
    </div>

    <!-- 身份证号 -->
    <p>*身份证号</p>

    <div class="idcard">
      {{ landlord.idcard }}
    </div>

    <!-- 证件照 -->
    <p>*证件照</p>

    <div class="image-idcard">
      <!-- 正面图 -->
      <img :src="landlord.idcardFront" alt="" />
      <!-- 反面图 -->
      <img :src="landlord.idcardOpposite" alt="" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      landlord: {}
    };
  },
  created() {
    this.fetchLandlordInfo();
  },
  methods: {
    async fetchLandlordInfo() {
      try {
        const { data } = await this.$axios.post("/landlord/listUser", {});
        this.landlord = data;
      } catch (error) {
        console.log("查询房东信息异常：", error);
      }
    }
  }
};
</script>

<style scoped lang="scss">
.landlord-container {
  background-color: rgb(255, 255, 255);
  padding: 20px;
  box-sizing: border-box;
  box-shadow: 0 4px 8px rgb(240, 240, 240);
}

.auth-status {
  background-color: rgb(246, 247, 248);
  padding: 20px 10px;
  margin-bottom: 20px;
  font-size: 14px;
}

.image-idcard {
  display: flex;
  gap: 20px;
  img {
    width: 200px;
    height: 140px;
  }
}

.idcard {
  margin-block: 20px;
  background-color: rgb(246, 247, 248);
  padding: 20px 10px;
  font-size: 24px;
  font-weight: 800;
}

.auth-user-info {
  display: flex;
  gap: 10px;
  align-items: center;

  img {
    width: 50px;
    height: 50px;
    border-radius: 5px;
  }

  div {
    font-size: 24px;
  }
}
</style>
