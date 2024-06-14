import { configureStore } from "@reduxjs/toolkit";
import combinedReducers from "./reducers";

const store = configureStore({ reducer: combinedReducers });

export { store };

