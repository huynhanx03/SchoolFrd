import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { getToken } from "./localStorageService";

export const getMyPosts = async (page) => {
  return await httpClient
    .get(API.MY_POST, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
      params: {
        page: page,
        size: 10,
      },
    });
};

export const createPost = async (post) => {
  const response = await httpClient.post(API.POST + "/create",
    post, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    }
  });

  return response;
};

export const getPosts = async (page) => {
  return await httpClient
    .get(API.POST + "/posts", {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
      params: {
        page: page,
        size: 10,
      },
    });
};
