export const SET_USER_INFO = "SET_USER_INFO";
export const setUserInfo = (userinfo) => ({
  type: SET_USER_INFO,
  userinfo,
});

export const RESET_USER_INFO = "RESET_USER_INFO"
export const resetUserInfo = () => ({
  type: RESET_USER_INFO,
});

export const ADD_TRAIL_TO_HISTORY = "ADD_TRAIL_TO_HISTORY";
export const addTrailToHistory = (historyEntry) => ({
  type: ADD_TRAIL_TO_HISTORY,
  historyEntry,
});
