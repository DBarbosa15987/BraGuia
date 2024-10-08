import { combineReducers } from "redux";
import { SET_APP_INFO, RESET_APP_DATA } from "./actions/appInfo";
import { SET_TRAILS, SET_PINS } from "./actions/trails";
import {
  ADD_TRAIL_TO_HISTORY,
  RESET_USER_INFO,
  SET_USER_INFO,
  CLEAR_USER_DATA,
  SET_BOOKMARKS
} from "./actions/user";

const initialState = {
  appData: {
    appinfo: {},
    trails: [],
    pins: [],
  },
  user: {
    info: {},
    history: [],
    bookmarks: [],
  },
};

const appDataReducer = (state = initialState, action) => {
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
        pins: [],
      };
    case SET_TRAILS:
      return {
        ...state,
        trails: action.trails,
      };
    case SET_PINS:
      return {
        ...state,
        pins: action.pins,
      };
    default:
      return state;
  }
};

const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case SET_USER_INFO:
      return {
        ...state,
        info: action.userinfo,
        history: [], //TODO: vai mesmo ser assim???
        bookmarks: [],
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
    case SET_BOOKMARKS: {
      return {
        ...state,
        bookmarks: action.bookmarks
      };
    }
    case CLEAR_USER_DATA:
      return {
        ...state,
        history: [],
        bookmarks: [],
      };
    default:
      return state;
  }
};

export const rootReducer = combineReducers({ appData: appDataReducer, user: userReducer });

