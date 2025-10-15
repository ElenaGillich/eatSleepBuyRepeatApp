export default function Login() {

    function login() {
        const host: string = window.location.host === "localhost:5173"
            ? "http://localhost:8080"
            : window.location.origin;

        window.open(host + "/oauth2/authorization/github", "_self");
    }

    return (
         <>
             <h4>You are not yet logged in.</h4>
            <button onClick={login}>Login</button>
         </>
    )
}