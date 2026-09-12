package com.example.PLAGIARISM_SERVICE.utils;

public final class CacheKeys {

    private CacheKeys() {
    }

    public static String check(Long checkId) {
        return "check:" + checkId;
    }

    public static String checksByPaper(
            Long paperId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return "paper:" +
                paperId +
                ":page:" +
                page +
                ":size:" +
                size +
                ":sortBy:" +
                sortBy +
                ":sortDirection:" +
                sortDirection;
    }

    public static String latestCheck(Long paperId) {
        return "paper:" + paperId + ":latest";
    }

    public static String matches(
            Long checkId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return "check:" +
                checkId +
                ":page:" +
                page +
                ":size:" +
                size +
                ":sortBy:" +
                sortBy +
                ":sortDirection:" +
                sortDirection;
    }

    public static String match(Long matchId) {
        return "match:" + matchId;
    }

    public static String myChecks(
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return "user:" +
                userId +
                ":page:" +
                page +
                ":size:" +
                size +
                ":sortBy:" +
                sortBy +
                ":sortDirection:" +
                sortDirection;
    }

    public static String allChecks(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return "all:" +
                "page:" +
                page +
                ":size:" +
                size +
                ":sortBy:" +
                sortBy +
                ":sortDirection:" +
                sortDirection;
    }

    public static String checkStatus(Long checkId) {
        return "check:" + checkId + ":status";
    }

    public static String paperStatus(Long paperId) {
        return "paper:" + paperId + ":status";
    }

    public static String report(Long checkId) {
        return "check:" + checkId;
    }

    public static String textIndex(Long paperId) {
        return "paper:" + paperId;
    }
}