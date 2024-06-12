export const SET_USER_INFO = "SET_USER_INFO";
export const setUserInfo = (userinfo) => ({
  type: SET_USER_INFO,
  userinfo,
});

export const RESET_USER_INFO = "RESET_USER_INFO"
export const resetUserInfo = () => ({
  type: RESET_USER_INFO,
});
