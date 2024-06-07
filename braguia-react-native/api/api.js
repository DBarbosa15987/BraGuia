import { updateAppInfo } from "@/state/actions/appInfo";
import { setTrails } from "@/state/actions/trails";
import { setUserInfo } from "@/state/actions/user";
import AsyncStorage from "@react-native-async-storage/async-storage";
import * as SecureStore from "expo-secure-store";

const BASE_URL = "http://192.168.85.186";

async function fetchWithCookies(url, options) {
  const cookie = await AsyncStorage.getItem("cookie");
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

// export async function fetchUser(dispatch) {
//     try {
//       const response = await fetchWithCookies(BASE_URL + '/user');
//       if (response.ok) {
//         const data = await response.json();
//         //dispatch(updateAppInfo(data[0]));

//       }
//     } catch (error) {
//       console.error("Error parsing User:", error);
//     }

//   }

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

async function storeCookie(cookie) {
  await SecureStore.setItemAsync("cookie", cookie);
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
    console.log("username: ", username, "password: ", password);
    const response = await fetch(BASE_URL + "/login", {
      credentials: "include",
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });
    if (response.ok) {
      // FIX: Cookies are not being recieved
      // at least in web need to test with android
      const cookies = response.headers.getSetCookie();

      // storeCookie(sanitizeCookies(cookies));
      return true;
    }
  } catch (error) {
    console.error("Error parsing User:", error);
  }
  return false;
}
