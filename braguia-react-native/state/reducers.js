import { combineReducers } from "redux";
import { SET_APP_INFO, RESET_APP_DATA } from "./actions/appInfo";
import { SET_TRAILS, SET_CURR_TRAIL } from "./actions/trails";
import {
  ADD_TRAIL_TO_HISTORY,
  RESET_USER_INFO,
  SET_USER_INFO,
} from "./actions/user";

const initialState = {
  user: {
    info: {},
    history: [],
    preferences: {},
  },
};

const appData = (state = initialState, action) => {
  switch (action.type) {
    case SET_APP_INFO:
      return {
        ...state,
        appinfo: action.payload,
      };
    case RESET_APP_DATA:
      return {
        appinfo: null,
        trails: [],
      };
    case SET_TRAILS:
      return {
        ...state,
        trails: action.trails,
      };
    default:
      return state;
  }
};

const user = (state = initialState, action) => {
  switch (action.type) {
    case SET_USER_INFO:
      return {
        ...state,
        info: action.userinfo,
      };
    case RESET_USER_INFO:
      return {
        ...state,
        info: null,
      };
    case ADD_TRAIL_TO_HISTORY:
      return {
        ...state,
        history: [action.historyEntry, ...state.history],
      };
    default:
      return state;
  }
};

const combinedReducers = combineReducers({ appData, user });

export default combinedReducers;
