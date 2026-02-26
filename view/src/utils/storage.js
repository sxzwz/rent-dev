// src/utils/storage.js

// 常量定义（全大写+下划线命名）
const TOKEN_KEY = "token";
const USER_INFO_KEY = "userInfo";
const ROLE_KEY = "role";
const LAST_VISITED_PATH_KEY = "last_visited_path";

// 登录状态使用 sessionStorage，保证多标签页各自独立（一标签页登录用户1，另一标签页登录管理员，互不覆盖）
const authStorage =
  typeof sessionStorage !== "undefined" ? sessionStorage : localStorage;

// ==================== Token 操作 ====================
export const getToken = () => {
  try {
    return authStorage.getItem(TOKEN_KEY);
  } catch (e) {
    console.error("Storage access error:", e);
    return null;
  }
};

export const setToken = token => {
  try {
    authStorage.setItem(TOKEN_KEY, token);
  } catch (e) {
    console.error("Storage access error:", e);
  }
};

export const clearToken = () => {
  try {
    authStorage.removeItem(TOKEN_KEY);
  } catch (e) {
    console.error("Storage access error:", e);
  }
};

// ==================== 用户信息操作 ====================
export const getUserInfo = () => {
  try {
    const json = authStorage.getItem(USER_INFO_KEY);
    return json ? JSON.parse(json) : null;
  } catch (e) {
    console.error("Storage access error:", e);
    return null;
  }
};

export const setUserInfo = userInfo => {
  try {
    authStorage.setItem(USER_INFO_KEY, JSON.stringify(userInfo));
  } catch (e) {
    console.error("Storage access error:", e);
  }
};

export const clearUserInfo = () => {
  try {
    authStorage.removeItem(USER_INFO_KEY);
  } catch (e) {
    console.error("Storage access error:", e);
  }
};

// ==================== 角色操作 ====================
export const getRole = () => {
  try {
    const role = authStorage.getItem(ROLE_KEY);
    return role ? Number(role) : null;
  } catch (e) {
    console.error("Storage access error:", e);
    return null;
  }
};

export const setRole = role => {
  try {
    authStorage.setItem(ROLE_KEY, Number(role));
  } catch (e) {
    console.error("Storage access error:", e);
  }
};

export const clearRole = () => {
  try {
    authStorage.removeItem(ROLE_KEY);
  } catch (e) {
    console.error("Storage access error:", e);
  }
};

// ==================== 路径记忆 ====================
export const getLastVisitedPath = () => {
  try {
    const path = localStorage.getItem(LAST_VISITED_PATH_KEY);
    return path || "/home"; // 默认返回首页
  } catch (e) {
    console.error("LocalStorage access error:", e);
    return "/home";
  }
};

export const setLastVisitedPath = path => {
  try {
    if (!["/login", "/register"].includes(path)) {
      localStorage.setItem(LAST_VISITED_PATH_KEY, path);
    }
  } catch (e) {
    console.error("LocalStorage access error:", e);
  }
};

export const clearLastVisitedPath = () => {
  try {
    localStorage.removeItem(LAST_VISITED_PATH_KEY);
  } catch (e) {
    console.error("LocalStorage access error:", e);
  }
};

// ==================== 通用方法 ====================
export const setStorage = (key, value) => {
  try {
    localStorage.setItem(key, JSON.stringify(value));
  } catch (e) {
    console.error("LocalStorage access error:", e);
  }
};

export const getStorage = key => {
  try {
    const value = localStorage.getItem(key);
    return value ? JSON.parse(value) : null;
  } catch (e) {
    console.error("LocalStorage access error:", e);
    return null;
  }
};

export const clearStorage = key => {
  try {
    localStorage.removeItem(key);
  } catch (e) {
    console.error("LocalStorage access error:", e);
  }
};

// ==================== 清空所有 ====================
export const clearAll = () => {
  try {
    authStorage.removeItem(TOKEN_KEY);
    authStorage.removeItem(USER_INFO_KEY);
    authStorage.removeItem(ROLE_KEY);
    localStorage.clear();
  } catch (e) {
    console.error("Storage access error:", e);
  }
};
