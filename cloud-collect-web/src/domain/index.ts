//REGION Frontend

export interface MenuItem {
  index?: string
  path: string
  name: string
}

export interface DropDownItem {
  command: string
  name: string
}

export interface Option<T extends string> {
  label: string
  value: T
}

export type DialogType = "none" | "login" | "register"

export type CollectSearchType = "none" | "name" | "category" | "categoryName" | "tag" | "tagName" | "type";

export type CategorySearchType = "none" | "name"

export type UserSearchType = "none" | "nickname" | "username" | "email"

export type SortType = "collect" | "category" | "tag" | "user"

//REGION Backend

export interface Collect {
  id?: number
  name: string
  summary: string
  url: string
  logoUrl: string
  category: Category | null
  tags: Tag[]
  type: CollectType
  user: User
  createdTime?: string
  lastModifiedTime?: string
  praised?: boolean
  praiseByUserCount?: number
  commentCount?: number
}

export interface Category {
  id?: number
  name: string
  summary: string
  user: User
  createdTime?: string
  lastModifiedTime?: string
  collectCount?: number
}

export interface Tag {
  id?: number
  name: string
  summary: string
  user: User
  createdTime?: string
  lastModifiedTime?: string
  collectCount?: number
}

export interface Comment {
  id?: number
  content: string
  collect: Collect
  sponsorByUser: User
  replyToComment?: Comment
  createdTime?: string
  replyToCommentCount?: number
}

export interface History {
  id?: number
  collect: Collect
  user: User
  createdTime?: string
}

export interface Notice {
  id?: number
  title: string
  content: string
  type: NoticeType
  user: User
  createdTime?: string
}

export interface User {
  id?: number
  username: string
  password: string
  email: string
  nickname: string
  introduce?: string
  avatarUrl?: string
  role?: Role
  activateStatus?: boolean
  registerTime?: string
  updateTime?: string
  followed?: boolean
  collectCount?: number
  categoryCount?: number
  praiseToCollectCount?: number
  followToUserCount?: number
  followByUserCount?: number
}

//REGION request

export interface LoginForm {
  username: string
  password: string
}

export interface ResetPasswordForm {
  username: string
  password: string
}

//REGION response

export interface UserDetailsVo extends UserDetails {
  delegateUser: User
}

export interface Url {
  url: string
}

export interface Message {
  message?: string
}

//REGION enums

export type CollectPrivacy = "PUBLIC" | "PRIVATE"

export type CollectType = "NONE" | "LOVE" | "IMPORTANT" | "TODO" | "DELAY"

export type NoticeType = "SYSTEM" | "ACCOUNT"

export type Role = "NORMAL" | "ADMIN"

export type DataType = "JSON" | "YAML" | "XML"

//REGION SpringBoot

//org.springframework.security.core.userdetails.UserDetails
export interface UserDetails {
  authorities: GrantedAuthority[]
  password: string
  username: string
  accountNonExpired: boolean
  accountNonLocked: boolean
  credentialsNonExpired: boolean
  enabled: boolean
}

//org.springframework.security.core.GrantedAuthority
export interface GrantedAuthority {
  authority: string
}

//org.springframework.data.domain.Page
export interface Page<T> {
  totalPages: number;
  totalElements: number;
  empty: boolean;
  number: number;
  size: number;
  numberOfElements: number;
  content: T[];
  sort: Sort;
  first: boolean;
  last: boolean;
  pageable: Pageable;
}

//for param `pageable: Pageable`
export interface PageableParam {
  page: number //default: 0
  size: number //default: 20
  sort?: string //expression: propName1, propName2, direction?
}

//org.springframework.data.domain.Pageable
export interface Pageable {
  paged: boolean;
  unpaged: boolean;
  pageNumber: number;
  pageSize: number;
  offset: number;
  sort: Sort;
}

//org.springframework.data.domain.Sort
export interface Sort {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}

export type Direction = "ASC" | "DESC"

//REGION Kotlin Stdlib

export interface Enum<T extends string> {
  name: T,
  text: string
}

export interface Pair<A, B> {
  first: A
  second: B
}

export interface Triple<A, B, C> {
  first: A
  second: B
  third: C
}
