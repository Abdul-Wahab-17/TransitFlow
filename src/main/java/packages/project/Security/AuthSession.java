package packages.project.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class AuthSession {
    public static final String LOGIN_ID = "authLoginId";
    public static final String ROLE = "authRole";

    private AuthSession() {
    }

    public static void signIn(HttpServletRequest request, Integer loginId, String role) {
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null) {
            existingSession.invalidate();
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(LOGIN_ID, loginId);
        session.setAttribute(ROLE, normalizeRole(role));
    }

    public static void signOut(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    public static Integer loginId(HttpSession session) {
        if (session == null) {
            return null;
        }

        Object loginId = session.getAttribute(LOGIN_ID);
        return loginId instanceof Integer ? (Integer) loginId : null;
    }

    public static String role(HttpSession session) {
        if (session == null) {
            return "";
        }

        Object role = session.getAttribute(ROLE);
        return role instanceof String ? (String) role : "";
    }

    public static boolean isRole(HttpSession session, String expectedRole) {
        return normalizeRole(expectedRole).equals(role(session));
    }

    public static boolean isAdmin(HttpSession session) {
        return isRole(session, "admin");
    }

    public static boolean isSuperAdmin(HttpSession session) {
        return isRole(session, "superadmin");
    }

    public static boolean isSameLogin(HttpSession session, Integer loginId) {
        Integer currentLoginId = loginId(session);
        return currentLoginId != null && currentLoginId.equals(loginId);
    }

    public static boolean canAccessOwnRole(HttpSession session, String expectedRole, Integer loginId) {
        return isRole(session, expectedRole) && isSameLogin(session, loginId);
    }

    public static String normalizeRole(String role) {
        return role == null ? "" : role.trim().toLowerCase();
    }
}
