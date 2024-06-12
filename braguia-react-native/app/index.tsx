import { fetchAppInfo } from "@/api/api";
import { Redirect } from "expo-router";
import { useDispatch } from "react-redux";

export default function InitialPage() {

  const dispatch = useDispatch()
  fetchAppInfo(dispatch)
  return <Redirect href="/home" />;
}
