import { combineReducers } from "redux";
import { SET_APP_INFO, RESET_APP_DATA } from "./actions/appInfo";
import { SET_TRAILS, SET_CURR_TRAIL } from "./actions/trails";
import { SET_USER_INFO } from "./actions/user";

const initialState = {};

const appInfo = (state = initialState, action) => {
  switch (action.type) {
    case SET_APP_INFO:
      return {
        ...state,
        appinfo: action.payload,
      };
    case RESET_APP_DATA:
      return {
        // appinfo: null,
        // trails: [],
      };
    default:
      return state;
  }
};

const trails = (state = initialState, action) => {
  switch (action.type) {
    case SET_TRAILS:
      return {
        ...state,
        trails: action.trails,
      };
    case SET_CURR_TRAIL:
      return {
        ...state,
        currTrail: action.payload,
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
    default:
      return state;
  }
};

const combinedReducers = combineReducers({ appInfo, trails, user });

export default combinedReducers;
