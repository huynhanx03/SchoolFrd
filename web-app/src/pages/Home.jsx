import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import {
  Box,
  TextField,
  Autocomplete,
  Card,
  CircularProgress,
  Typography,
  Button
} from "@mui/material";
import { isAuthenticated } from "../services/authenticationService";
import Scene from "./Scene";
import Post from "../components/Post";
import { getPosts, createPost } from "../services/postService";
import { logOut } from "../services/authenticationService";
import { getSchools } from "../services/schoolService";

export default function Home() {
  const [posts, setPosts] = useState([]);
  const [schools, setSchools] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  const [hasMore, setHasMore] = useState(false);
  const observer = useRef();
  const lastPostElementRef = useRef();

  const navigate = useNavigate();

  const [content, setContent] = useState("");
  const [selectedSchool, setSelectedSchool] = useState(null);

  const handleSubmit = () => {
    if (!content.trim()) return; // Prevent empty posts
    createPost({ content, schoolId: selectedSchool?.id })
      .then((response) => {
        console.log(response);
        setContent(""); // Clear input after submission
        setSelectedSchool(null);
      }).catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    if (!isAuthenticated()) {
      navigate("/login");
    } else {
      loadPosts(page);
    }
  }, [navigate, page]);

  const loadPosts = (page) => {
    console.log(`loading posts for page ${page}`);
    setLoading(true);
    getPosts(page)
      .then((response) => {
        console.log(response.data);
        setTotalPages(response.data.data.totalPages);
        setPosts((prevPosts) => [...prevPosts, ...response.data.data.data]);
        setHasMore(response.data.data.length > 0);
        console.log("loaded posts:", response.data.result);
      })
      .catch((error) => {
        if (error.response.status === 401) {
          logOut();
          navigate("/login");
        }
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    if (!hasMore) return;

    if (observer.current) observer.current.disconnect();
    observer.current = new IntersectionObserver((entries) => {
      if (entries[0].isIntersecting) {
        if (page < totalPages) {
          setPage((prevPage) => prevPage + 1);
        }
      }
    });
    if (lastPostElementRef.current) {
      observer.current.observe(lastPostElementRef.current);
    }

    setHasMore(false);
  }, [hasMore]);

  const loadSchools = () => {
    getSchools()
      .then((response) => {
        console.log(response.data);
        setSchools(response.data.data);
      })
      .catch((error) => {
        console.log(error);
      })
  };

  useEffect(() => {
    loadSchools();
  }, []);

  return (
    <Scene>
      <Card
        sx={{
          minWidth: 500,
          maxWidth: 600,
          boxShadow: 3,
          borderRadius: 2,
          padding: "20px",
        }}
      >
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-start",
            gap: "10px",
            marginBottom: "20px",
          }}
        >
          <Typography variant="h6">Create a New Post</Typography>
          <TextField
            fullWidth
            multiline
            rows={3}
            label="What's on your mind?"
            value={content}
            onChange={(e) => setContent(e.target.value)}
          />
          <Autocomplete
            options={schools}
            getOptionLabel={(option) => option.name}
            value={selectedSchool}
            onChange={(e, newValue) => setSelectedSchool(newValue)}
            renderInput={(params) => (
              <TextField {...params} label="Tag a School" />
            )}
            fullWidth
          />
          <Button variant="contained" onClick={handleSubmit}>
            Post
          </Button>
        </Box>

        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-start",
            width: "100%",
            gap: "10px",
          }}
        >
          <Typography
            sx={{
              fontSize: 18,
              mb: "10px",
            }}
          >
            Post:
          </Typography>

          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              justifyContent: "space-between",
              alignItems: "flex-start",
              width: "100%", // Ensure content takes full width
            }}
          ></Box>

          {posts.map((post, index) => {
            if (posts.length === index + 1) {
              return (
                <Post ref={lastPostElementRef} key={post.id} post={post} />
              );
            } else {
              return <Post key={post.id} post={post} />;
            }
          })}
          {loading && (
            <Box
              sx={{ display: "flex", justifyContent: "center", width: "100%" }}
            >
              <CircularProgress size="24px" />
            </Box>
          )}
        </Box>
      </Card>
    </Scene>
  );
}
