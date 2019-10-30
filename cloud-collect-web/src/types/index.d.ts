//REGION Frontend

export interface NavItem {
  index: string
  path: string
  name: string
}

//REGION Backend

//NOTE 这里不能直接使用Collect作为接口名，因为与组件名重复了，是否可考虑使用ICollect？

export interface Collect {
  id?: number
  name: string
  summary: string
  url: string
  logoUrl: string
  category?: CollectCategory
  tags?: CollectTag[]
  type?: CollectType
  user?: User
  createdTime?: string
  lastModifiedTime?: string
  praiseByUserList?: User[] | null
  praiseByUserCount?: number
  commentCount?: number
}

export interface CollectCategory {
  id?: number
  name: string
  summary: string
  user?: User
  createdTime?: string
  lastModifiedTime?: string
  collectCount?: number
}

export interface CollectTag {
  id?: number
  name: string
  summary: string
  user?: User
  createdTime?: string
  lastModifiedTime?: string
  collectCount?: number
}

export interface Comment {
  id?: number
  content: string
  collect: Collect
  sponsorByUser?: User
  replyToComment?: Comment
  createdTime?: string
  replyByCommentCount?: number
}

export interface History {
  id?: number
  collect: Collect
  user?: User
  createdTime?: string
}

export interface Notice {
  id?: number
  title: string
  content: string
  type: NoticeType
  user?: User
  readStatus?: boolean
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
  backgroundUrl?: string
  role?: Role
  activateStatus?: boolean
  registerTime?: string
  updateTime?: string
  followToUserList?: User[]
  followByUserList?: User[]
  praiseToCollectList?: Collect[]
  followToUserCount?: number
  followByUserCount?: number
  collectCount?: number
  commentCount?: number
  noticeCount?: number
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

//REGION enums

export type CollectPrivacy = "PUBLIC" | "PRIVATE"

export type CollectType = "NONE" | "LOVE" | "IMPORT" | "TODO" | "DELAY"

export type DataType = "JSON" | "YAML" | "XML" | "PROPERTIES"

export type NoticeType = "SYSTEM" | "ACCOUNT" | "HELLO"

export type Role = "NORMAL" | "ADMIN"

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

//for param `@PageableDefault pageable: Pageable`
export interface PageableParam {
  page: number //default: 0
  size: number //default: 20
  sort: string[] //expression: propName1, propName2, direction?
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

//org.springframework.validation.FieldError
export interface FieldError extends ObjectError {
  field: string;
  rejectedValue: object | null;
  bindingFailure: boolean;
}

//org.springframework.validation.ObjectError
export interface ObjectError {
  objectName: string;
  code: string | null;
  codes: string[] | null;
  arguments: object[] | null;
  defaultMessage: string | null;
}

//REGION Kotlin Stdlib

export interface Pair<A, B> {
  first: A
  second: B
}

export interface Triple<A, B, C> {
  first: A
  second: B
  third: C
}
