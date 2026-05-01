"use client";
import HideIcon from "@/public/hide.png";
import ShowIcon from "@/public/view.png";
import Image from "next/image";
import { FormEvent, useState } from "react";
export default function PasswordInput({
  name,
  placeholder,
  value,
  onChange,
}: {
  name: string;
  placeholder: string;
  value: string;
  onChange: (e: FormEvent) => void;
}) {
  const [ShowPassword, setShowPassword] = useState(false);
  const ShowPasswordHandle = () => {
    setShowPassword(!ShowPassword);
  };
  return (
    <div className="grid grid-cols-1 grid-rows-1">
      <input
        id={`${name}-passwordField`}
        type={ShowPassword ? "text" : "password"}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className="col-start-1 row-start-1 bg-white border rounded-sm px-4 py-4 focus:outline-(--primary-color)"
      />
      <div className="col-start-1 row-start-1 ml-auto flex px-4">
        <button
          id={`${name}-passwordShowBtn`}
          onClick={ShowPasswordHandle}
          type="button"
          className="cursor-pointer"
        >
          {ShowPassword ? (
            <Image
              id={`${name}-showIcon`}
              src={ShowIcon}
              alt=""
              className="size-7"
            />
          ) : (
            <Image
              id={`${name}-hideIcon`}
              src={HideIcon}
              alt=""
              className="size-7"
            />
          )}
        </button>
      </div>
    </div>
  );
}
