import { updateAppInfo } from "@/state/actions/appInfo";
import { setTrails } from "@/state/actions/trails";
import { resetUserInfo, setUserInfo } from "@/state/actions/user";
import * as SecureStore from "expo-secure-store";

const BASE_URL = "http://192.168.85.186";
const COOKIE_KEY = "cookie";

async function fetchWithCookies(url, options = {}) {
  const cookie = await SecureStore.getItemAsync(COOKIE_KEY);
  if (cookie) {
    options.headers = {
      ...options.headers,
      Cookie: cookie,
    };
  }
  return fetch(url, options);
}

export async function fetchAppInfo(dispatch) {
  try {
    const response = await fetchWithCookies(BASE_URL + "/app");
    if (response.ok) {
      const data = await response.json();
      dispatch(updateAppInfo(data[0]));
    }
  } catch (error) {
    console.error("Error parsing AppInfo:", error);
  }
}
export async function fetchTrails(dispatch) {
  try {
    const response = await fetchWithCookies(BASE_URL + "/trails");
    if (response.ok) {
      const data = await response.json();
      dispatch(setTrails(data));
    }
  } catch (error) {
    console.error("Error parsing Trails:", error);
  }
}

export async function fetchUserInfo(dispatch) {
  try {
    const response = await fetchWithCookies(BASE_URL + "/user");
    if (response.ok) {
      const data = await response.json();
      dispatch(setUserInfo(data));
    }
  } catch (error) {
    console.error("Error while fetching Userinfo:", error);
  }
}

function sanitizeCookies(cookies) {
  const csrfTokenMatch = cookies.match(/csrftoken=([^;]+)/);
  const sessionIdMatch = cookies.match(/sessionid=([^;]+)/);
  const csrfToken = csrfTokenMatch ? csrfTokenMatch[0] : null;
  const sessionId = sessionIdMatch ? sessionIdMatch[0] : null;
  return csrfToken + "; " + sessionId;
}

export async function login(username, password) {
  try {
    const response = await fetch(BASE_URL + "/login", {
      credentials: "omit",
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });
    if (response.ok) {
      const cookies = response.headers.get("set-cookie");
      const sanitizedCookie = sanitizeCookies(cookies);
      await SecureStore.setItemAsync(COOKIE_KEY, sanitizedCookie);
      return true;
    }
  } catch (error) {
    console.error("Error parsing User:", error);
  }
  return false;
}

export async function logout(dispatch) {
  
  try {
    const response = await fetchWithCookies(BASE_URL + "/logout", {
      method: 'POST'
    });
    await SecureStore.deleteItemAsync(COOKIE_KEY);
    console.log("Logout got response :" + response.status)
    dispatch(resetUserInfo());

  }catch (error) {
    console.error("Error parsing User:", error);
  }

}
