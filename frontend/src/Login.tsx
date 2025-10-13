import {useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function Login() {

    const nav = useNavigate();

    function login() {
        const host: string = window.location.host === "localhost:5173"
            ? "http://localhost:8080"
            : window.location.origin;

        window.open(host + "/oauth2/authorization/github", "_self");
    }

    const loadUser = () => {
        axios.get("/api/auth/me")
            .then(() => nav("/home"))
            .catch((e) => console.error(e))
    }

    useEffect(() => {
        loadUser();
    }, []);

    return (
         <>
            <button onClick={login}>Login</button>
         </>
    )
}