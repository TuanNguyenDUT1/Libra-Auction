import Logo from "@/components/logo";
import Nav from "@/components/nav";
import UserAction from "@/components/user_action";
import { isAuthenticated } from "@/lib/is_authenticated";

import { NavType } from "@/types/nav_type";
import { UserActionType } from "@/types/user_action_type";
export default async function Header() {
  const authed = await isAuthenticated();
  const navItems: NavType[] = [
    { value: "Categories", href: "/categories" },
    { value: "Auctions", href: "/auctions" },
    { value: "Support", href: "/support" },
    { value: "Contact", href: "/contact" },
  ];
  const userActionItems: UserActionType[] = [
    { value: "Sign in", href: "/sign-in" },
    { value: "Sign up", href: "/sign-up" },
  ];
  const userActionItemsForAuthenticated: UserActionType[] = [
    { value: "Sign out", href: "/api/sign-out" },
  ];
  return (
    <div className="px-8 py-4 flex bg-white shadow-md">
      <Logo textColor="color" />
      <Nav items={navItems} />
      {authed && <UserAction items={userActionItemsForAuthenticated}/>}
      {!authed && <UserAction items={userActionItems} />}
    </div>
  );
}
