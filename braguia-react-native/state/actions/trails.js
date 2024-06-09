export const SET_TRAILS = "SET_TRAILS";
export const setTrails = (trails) => ({
  type: SET_TRAILS,
  trails,
});

export const SET_CURR_TRAIL = "SET_CURR_TRAIL";
export const setCurrTrail = (trail) => ({
  type: SET_CURR_TRAIL,
  payload: trail,
});
