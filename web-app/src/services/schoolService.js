import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { getToken } from "./localStorageService";

export const getSchools = async () => {
  return await httpClient
    .get(API.SCHOOL, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      }
    });
};
