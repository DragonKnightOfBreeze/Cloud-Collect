//REGION entity

//NOTE 这里不能直接使用Collect作为接口名，因为与组件名重复了，是否可考虑使用ICollect？

export interface CollectData {
  id: number
  name: string
  summary: string
  url: string
  logoUrl: string
  category: CollectCategoryData
  tags: CollectTagData[]
  type: CollectType
  user: UserData
  createdTime: string
  lastModifiedTime: string
  praiseByUserList: UserData[] | null
  praiseByUserCount: number
  commentCount: number
}

export interface CollectCategoryData {
  id: number
  name: string
  summary: string
  user: UserData
  createdTime: string
  lastModifiedTime: string
  collectCount: number
}

export interface CollectTagData {
  id: number
  name: string
  summary: string
  user: UserData
  createdTime: string
  lastModifiedTime: string
  collectCount: number
}

export interface CommentData {
  id: number
  content: string
  collect: CollectData
  sponsorByUser: UserData
  replyToComment: CommentData | null
  createdTime: string
  replyByCommentCount: number
}

export interface HistoryData {
  id: number
  collect: CollectData
  user: UserData
  createdTime: string
}

export interface NoticeData {
  id: number
  title: string
  content: string
  type: NoticeType
  user: UserData
  readStatus: boolean
  createdTime: string
}

export interface UserData {
  id: number
  username: string
  password: string
  email: string
  nickname: string
  introduce: string
  avatarUrl: string
  backgroundUrl: string
  role: Role
  activateStatus: boolean
  registerTime: string
  updateTime: string
  followToUserList: UserData[] | null
  followByUserList: UserData[] | null
  praiseToCollectList: CollectData[] | null
  followToUserCount: number
  followByUserCount: number
  collectCount: number
  commentCount: number
  noticeCount: number
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
  delegateUser: UserData
}

//REGION enums

export type CollectPrivacy = "PUBLIC" | "PRIVATE"

export type CollectType = "NONE" | "LOVE" | "IMPORT" | "TODO" | "DELAY"

export type DataType = "JSON" | "YAML" | "XML" | "PROPERTIES"

export type NoticeType = "SYSTEM" | "ACCOUNT" | "HELLO"

export type Role = "NORMAL" | "ADMIN"

//REGION external

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

//org.springframework.data.domain.Sort
export interface Sort {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
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
