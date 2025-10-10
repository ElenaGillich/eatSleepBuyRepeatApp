import {Link} from "react-router-dom";

export default function Navbar() {

    return (

        <header className="header">
            <div className="logo-container">
                <img src="/logo.png" alt="Logo" className="logo"/>
            </div>
            <nav className={"navbar"}>
                <Link className={"nav-button"} to={"/"}>Home</Link>
                <Link className={"nav-button"} to={"/allProducts"}>All Products</Link>
                <Link className={"nav-button"} to={"/allGroceryLists"}>All Grocery Lists</Link>
            </nav>
        </header>
    )
}