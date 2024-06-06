import { updateAppInfo } from '@/state/actions/appInfo';
import { setTrails } from '@/state/actions/trails';

const BASE_URL = 'http://192.168.85.186';

export async function fetchAppInfo(dispatch) {
    try {
      const response = await fetch(BASE_URL + '/app');
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
      const response = await fetch(BASE_URL + '/trails');
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
//       const response = await fetch(BASE_URL + '/user');
//       if (response.ok) {
//         const data = await response.json();
//         //dispatch(updateAppInfo(data[0]));
        
//       }
//     } catch (error) {
//       console.error("Error parsing User:", error);
//     }

//   }