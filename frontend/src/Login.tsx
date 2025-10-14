import {useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

type LoginProps = {
    setUser: (user: string|null|undefined) => void
}

export default function Login(props: Readonly<LoginProps>) {

    const nav = useNavigate();

    function login() {
        const host: string = window.location.host === "localhost:5173"
            ? "http://localhost:8080"
            : window.location.origin;

        window.open(host + "/oauth2/authorization/github", "_self");
    }

    const loadUser = () => {
        axios.get("/api/auth/me")
            .then((response) => {
                props.setUser(response.data);
                nav("/home");
            })
            .catch((e) => {
                props.setUser(null)
                console.error(e)
            })
    }

    useEffect(() => {
        loadUser();
    }, []);

    return (
         <>
             <p>You are not logged in</p>
            <button onClick={login}>Login</button>
         </>
    )
}