import { } from 'react-redux';
import { configureStore } from '@reduxjs/toolkit'
// import thunk from 'redux-thunk';
import combinedReducers from './reducers';

// import { persistStore, persistReducer } from 'redux-persist';
// import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';
// import AsyncStorage from '@react-native-async-storage/async-storage';

// const persistConfig = {
//     key: 'root',
//     storage: AsyncStorage,
//     stateReconciler: autoMergeLevel2,
// }




// const persistedReducer = persistReducer(persistConfig, reducers);
// const store = createStore(persistedReducer, initialState, applyMiddleware(thunk));
// const persistor = persistStore(store);

const store = configureStore({reducer:combinedReducers})

export { store };