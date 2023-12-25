package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    protected ConcurrentHashMap<Long, String> postsList = new ConcurrentHashMap<>();
    protected AtomicLong postCounter = new AtomicLong(0);

    public List<Post> all() {
        ArrayList<Post> allPosts = new ArrayList<>();
        for (int i = 1; i < postsList.size(); i++) {
            allPosts.add(new Post(i, postsList.get(i)));
        }
        return allPosts;
    }

    public Optional<Post> getById(long id) {
        Post post = null;
        if (postsList.containsKey(id)) {
            post = new Post(id, postsList.get(id));
        }
        return Optional.ofNullable(post);
    }

    public Post save(Post post) {
        Post newPost;
        if (post.getId() == 0) {
            postCounter.getAndIncrement();
            postsList.put(postCounter.longValue(), post.getContent());
            newPost = new Post(postCounter.longValue(), post.getContent());
        } else {
            postsList.put(post.getId(), post.getContent());
            newPost = new Post(post.getId(), post.getContent());
        }
        return newPost;
    }

    public void removeById(long id) {
        if (!this.getById(id).equals(Optional.empty())) {
            postsList.remove(id);
            postCounter.getAndDecrement();
        }
    }
}
