import type {Product} from "../model/Product.tsx";
import './ProductCard.css'
import {useState} from "react";

type ProductCardProps = {
    product: Product
}

export default function ProductCard(props: Readonly<ProductCardProps>){

    const [isEditing, setIsEditing] = useState(false);

    return(
            <div className={"product"}>
                {!isEditing && <h2>{props.product.name}</h2>}
                {isEditing && <input type={"text"} value={props.product.name}/>}

                <h3>ID: {props.product.id}</h3>
                {!isEditing && <button onClick={() =>setIsEditing(!isEditing)}> ✎Edit</button>}
                {isEditing && <button onClick={() =>setIsEditing(!isEditing)}>Save</button>}
            </div>
    )
}